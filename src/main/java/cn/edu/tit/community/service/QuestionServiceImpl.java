package cn.edu.tit.community.service;

import cn.edu.tit.community.mapper.QuestionMapper;
import cn.edu.tit.community.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    QuestionMapper questionMapper;

    @Override
    public boolean addQuestion(Question question) {
        int mysql_affected_rows = questionMapper.insertQuestion(question);
        boolean result = mysql_affected_rows != 0 ? true : false;
        return result;
    }
}
