package cn.edu.tit.community.service;

import cn.edu.tit.community.dto.PageInfoDTO;
import cn.edu.tit.community.dto.QuestionDTO;
import cn.edu.tit.community.model.Question;

import java.util.List;


public interface QuestionService {
    /**
     * 发布问题
     *
     * @param question 发起的问题对象
     * @return 发起成功返回true, 失败返回false
     */
    public boolean addQuestion(Question question);

    /**
     * 分页查询问题
     *
     * @param offset   分页的起始索引
     * @param pageSize 分页时每页个数
     * @return 查询到的问题集合
     */
    List<QuestionDTO> findQuestion(int offset, int pageSize);

    /**
     * 查询数据库中记录的问题总数
     *
     * @return 数据库中记录的问题总个数
     */
    public int findQuestionCount();

    /**
     * 用于获得前端分页使用的信息
     *
     * @param currPage 当前页码
     * @param pageSize 每页问题个数
     * @return 获取到的分页信息
     */
    PageInfoDTO getPageInfo(int currPage, int pageSize);
}
