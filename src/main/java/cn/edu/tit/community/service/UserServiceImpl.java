package cn.edu.tit.community.service;

import cn.edu.tit.community.mapper.UserMapper;
import cn.edu.tit.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;

/**
 * 用户Service接口实现类
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean addUser(User user) {
        int mysql_affected_rows = userMapper.insertUer(user);
        boolean result = mysql_affected_rows != 0 ? true : false;
        return result;
    }

    @Override
    public User findUserByToken(String token) {
        return userMapper.selectUserByToken(token);
    }

    @Override
    public User findUserById(int id) {
        User user = userMapper.selectUserByID(id);
        return user;
    }

    @Override
    public void createOrUpdateUser(User user) {
        User dbUser = userMapper.selectUSerByAccountID(user.getAccountId());

        if (dbUser == null) {
            // 用户不存在，执行插入操作
            userMapper.insertUer(user);
        } else {
            // 用户存在，实行更新操作
            dbUser.setName(user.getName()); // 用户名
            dbUser.setToken(user.getToken());// 用户token
            dbUser.setBio(user.getBio());// 用户个签
            dbUser.setAvatarUrl(user.getAvatarUrl());// 用户头像
            dbUser.setGmtModify(System.currentTimeMillis());// 记录修改时间
            userMapper.updateUser(dbUser);
        }
    }
}
