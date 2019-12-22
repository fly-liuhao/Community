package cn.edu.tit.community.mapper;

import cn.edu.tit.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;


@Mapper
@Repository
public interface UserMapper {

    @Insert("insert into user(name,token,account_id,gmt_create,gmt_modify,bio,avatar_url) values(#{name},#{token},#{accountId},#{gmtCreate},#{gmtModify},#{bio},#{avatarUrl})")
    public int insertUer(User user);

    @Select("select * from user where token=#{token}")
    User selectUserByToken(@Param("token") String token);

    @Select("select * from user where id=#{id}")
    User selectUserByID(@Param("id") int id);

    @Select("select * from user where account_id=#{accountId}")
    User selectUSerByAccountID(@Param("accountId") String accountId);

    @Select("update user set name=#{name}, token=#{token}, gmt_modify=#{gmtModify}, bio=#{bio}, avatar_url=#{avatarUrl} where id=#{id}")
    void updateUser(User user);
}
