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

    /**
     * 根据token查询用户
     *
     * @param token 登录时UUID生成记录的标记
     * @return 查询到的用户对象
     */
    User findUserByToken(String token);

    /**
     * 根据用户id查询用户
     *
     * @param id 用户ID
     * @return 查询到的用户对象
     */
    User findUserById(Long id);
}
