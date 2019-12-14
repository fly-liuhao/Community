package cn.edu.tit.community.service;

import cn.edu.tit.community.model.Question;


public interface QuestionService {
    /**
     * 发布问题
     *
     * @param question 发起的问题对象
     * @return 发起成功返回true, 失败返回false
     */
    public boolean addQuestion(Question question);
}
