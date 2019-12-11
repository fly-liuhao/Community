package cn.edu.tit.community.mapper;

        import cn.edu.tit.community.model.User;
        import org.apache.ibatis.annotations.Insert;
        import org.apache.ibatis.annotations.Mapper;
        import org.springframework.stereotype.Repository;


@Mapper
@Repository
public interface UserMapper {

    @Insert("insert into user(name,token,account_id,gmt_create,gmt_modify) values(#{name},#{token},#{accountId},#{gmtCreate},#{gmtModify})")
    public int insertUer(User user);
}
