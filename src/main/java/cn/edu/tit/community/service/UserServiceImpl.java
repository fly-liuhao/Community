package cn.edu.tit.community.service;

import cn.edu.tit.community.mapper.UserMapper;
import cn.edu.tit.community.model.User;
import cn.edu.tit.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import java.util.List;

/**
 * 用户Service接口实现类
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean addUser(User user) {
        int mysql_affected_rows = userMapper.insert(user);
        boolean result = mysql_affected_rows != 0 ? true : false;
        return result;
    }

    @Override
    public User findUserByToken(String token) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andTokenEqualTo(token);
        final List<User> users = userMapper.selectByExample(userExample);
        if (users.isEmpty()) {
            return null;
        } else {
            return users.get(0);
        }
    }

    @Override
    public User findUserById(int id) {
        User user = userMapper.selectByPrimaryKey(id);
        return user;
    }

    @Override
    public void createOrUpdateUser(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<User> dbUsers = userMapper.selectByExample(userExample);

        if (dbUsers.isEmpty()) {
            // 用户不存在，执行插入操作
            userMapper.insert(user);
        } else {
            // 用户存在，实行更新操作
            User dbUser = dbUsers.get(0);
            User updateUser = new User();
            updateUser.setName(user.getName()); // 用户名
            updateUser.setToken(user.getToken());// 用户token
            updateUser.setBio(user.getBio());// 用户个签
            updateUser.setAvatarUrl(user.getAvatarUrl());// 用户头像
            updateUser.setGmtModify(System.currentTimeMillis());// 记录修改时间
            UserExample example = new UserExample();
            example.createCriteria()
                    .andIdEqualTo(dbUser.getId());
            userMapper.updateByExampleSelective(updateUser, example);
        }
    }
}
