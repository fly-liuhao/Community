package cn.edu.tit.community.service;

import cn.edu.tit.community.model.User;

/**
 * 用户Service接口
 */
public interface UserService {

    /**
     * 添加用户
     *
     * @param user 用户对象
     * @return 成功返回true，失败返回false
     */
    public boolean addUser(User user);
}
