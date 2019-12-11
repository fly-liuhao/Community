package cn.edu.tit.community.service;

import cn.edu.tit.community.mapper.UserMapper;
import cn.edu.tit.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
