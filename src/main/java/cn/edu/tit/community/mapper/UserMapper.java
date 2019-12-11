package cn.edu.tit.community.mapper;

        import cn.edu.tit.community.model.User;
        import org.apache.ibatis.annotations.Insert;
        import org.apache.ibatis.annotations.Mapper;
        import org.apache.ibatis.annotations.Select;
        import org.springframework.stereotype.Repository;


@Mapper
@Repository
public interface UserMapper {

    @Insert("insert into user(name,token,account_id,gmt_create,gmt_modify,bio) values(#{name},#{token},#{accountId},#{gmtCreate},#{gmtModify},#{bio})")
    public int insertUer(User user);

    @Select("select * from user where token=#{token}")
    User selectUserByToken(String token);
}
