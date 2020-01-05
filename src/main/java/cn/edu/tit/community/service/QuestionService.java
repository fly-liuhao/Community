package cn.edu.tit.community.service;

import cn.edu.tit.community.dto.CommentDTO;
import cn.edu.tit.community.dto.PageInfoDTO;
import cn.edu.tit.community.dto.QuestionDTO;
import cn.edu.tit.community.dto.SearchQuestionDTO;
import cn.edu.tit.community.model.Question;

import java.util.List;

public interface QuestionService {


    /**
     * 查询数据库指定ID用户发布的问题总数
     *
     * @param creator 用户ID
     * @return 该用户发布的问题总个数
     */
    int findQuestionCountByCreator(int creator);

    /**
     * 分页查询问题
     *
     * @param offset   分页的起始索引
     * @param pageSize 分页时每页个数
     * @param creator  用户ID
     * @return 查询到的某用户发布的问题集合
     */
    List<QuestionDTO> findQuestionByCreator(int offset, Integer pageSize, int creator);

    /**
     * 查询数据库指定ID用户发布的问题总数
     *
     * @param keyword 问题的关键字
     * @return 该用户发布的问题总个数
     */
    int findQuestionCountByTitle(String keyword);

    /**
     * 分页查询问题（按照问题标题关键字）
     *
     * @param searchQuestionDTO 封装分页信息以及关键字的模型
     * @return 查询到的某用户发布的问题集合
     */
    List<QuestionDTO> findQuestionByTitle(SearchQuestionDTO searchQuestionDTO);

    /**
     * 根据问题的id查询问题
     *
     * @param id 问题ID
     * @return 查询到的问题实体
     */
    QuestionDTO getQuestionByID(int id);

    /**
     * 添加或者修改问题
     *
     * @param question 传类的问题对象
     */
    void addOrModifyQuestion(Question question);

    /**
     * 实现阅读数的增加
     *
     * @param id   问题ID
     * @param step 步长
     * @return 数据库影响的行数
     */
    void incViewCount(int id, int step);

    /**
     * 获取问题的相似问题
     *
     * @param questionDTO 原始问题实体
     * @return 查找到的相似问题集合
     */
    List<QuestionDTO> getSimilarQuestion(QuestionDTO questionDTO);
}
