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
    User selectUserByID(@Param("id") Long id);
}
