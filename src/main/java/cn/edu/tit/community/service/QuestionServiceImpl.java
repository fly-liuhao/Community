package cn.edu.tit.community.service;

import cn.edu.tit.community.dto.QuestionDTO;
import cn.edu.tit.community.mapper.QuestionMapper;
import cn.edu.tit.community.model.Question;
import cn.edu.tit.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    UserService userService;

    @Override
    public boolean addQuestion(Question question) {
        int mysql_affected_rows = questionMapper.insertQuestion(question);
        boolean result = mysql_affected_rows != 0 ? true : false;
        return result;
    }

    @Override
    public List<QuestionDTO> findAllQuestion() {
        List<Question> questionList = questionMapper.selectAllQuestion();
        List<QuestionDTO> questionDtoList = new ArrayList<QuestionDTO>();
        for (Question question : questionList) {
            QuestionDTO questionDTO = new QuestionDTO();
            // 将两个对象中相同字段的属性值自动拷贝到另一个实体对象中去
            BeanUtils.copyProperties(question, questionDTO);
            User user = userService.findUserById(question.getCreator());
            questionDTO.setUser(user);
            System.out.println(questionDTO);
            questionDtoList.add(questionDTO);
        }
        return questionDtoList;
    }
}
