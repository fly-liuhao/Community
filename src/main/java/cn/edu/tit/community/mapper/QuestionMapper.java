package cn.edu.tit.community.mapper;

import cn.edu.tit.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface QuestionMapper {

    @Insert("insert into question(title,description,tag,comment_count,view_count,like_count,gmt_create,gmt_modify,creator) values(#{title},#{description},#{tag},#{commentCount},#{viewCount},#{likeCount},#{gmtCreate},#{gmtModify},#{creator})")
    public int insertQuestion(Question question);
}
