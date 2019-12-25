package cn.edu.tit.community.mapper;

public interface CommentExtMapper {

    /**
     * 实现评论回复数的增加
     * @param id 回复的评论ID
     * @param step 步长
     * @return 数据库影响的行数
     */
    int  incCommentCount(Integer id, int step);
}
