package cn.edu.tit.community.mapper;

import cn.edu.tit.community.model.Question;
import cn.edu.tit.community.model.QuestionExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * 问题mapper的补充，防止表结构改变时，重新生成mapper时出现覆盖
 */
public interface QuestionExtMapper {

    /**
     * 实现问题阅读数的增加
     * @param id 问题ID
     * @param step 步长
     * @return 数据库影响的行数
     */
    int incViewCount(@Param("id") int id, @Param("step") int step);

    /**
     * 实现问题评论数的增加
     * @param id 问题ID
     * @param step 步长
     * @return 数据库影响的行数
     */
    int  incCommentCount(Integer id, int step);
}