package cn.edu.tit.community.service;

import cn.edu.tit.community.dto.PageInfoDTO;
import cn.edu.tit.community.dto.QuestionDTO;
import cn.edu.tit.community.mapper.QuestionMapper;
import cn.edu.tit.community.model.Question;
import cn.edu.tit.community.model.QuestionExample;
import cn.edu.tit.community.model.User;
import org.apache.ibatis.session.RowBounds;
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
        int mysql_affected_rows = questionMapper.insert(question);
        boolean result = mysql_affected_rows != 0 ? true : false;
        return result;
    }

    @Override
    public int findQuestionCount() {
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria();
        return (int) questionMapper.countByExample(questionExample);
    }

    @Override
    public int findQuestionCountByID(int creator) {
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(creator);
        return (int) questionMapper.countByExample(questionExample);
    }

    @Override
    public List<QuestionDTO> findQuestion(int offset, int pageSize) {

        // 获取分页后的问题集合
        QuestionExample example = new QuestionExample();
        example.createCriteria();
        List<Question> questionList = questionMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, pageSize));
        // 用于传输时的问题集合（补充了创建问题的用户信息）
        List<QuestionDTO> questionDtoList = new ArrayList<QuestionDTO>();
        for (Question question : questionList) {
            QuestionDTO questionDTO = new QuestionDTO();
            // 将两个对象中相同字段的属性值自动拷贝到另一个实体对象中去
            BeanUtils.copyProperties(question, questionDTO);
            User user = userService.findUserById(question.getCreator());
            questionDTO.setUser(user);
            questionDtoList.add(questionDTO);
        }
        return questionDtoList;
    }

    @Override
    public List<QuestionDTO> findQuestionByID(int offset, Integer pageSize, int creator) {
        // 获取分页后的问题集合
        QuestionExample example = new QuestionExample();
        example.createCriteria().andCreatorEqualTo(creator);
        List<Question> questionList = questionMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, pageSize));
        // 用于传输时的问题集合（补充了创建问题的用户信息）
        List<QuestionDTO> questionDtoList = new ArrayList<QuestionDTO>();
        for (Question question : questionList) {
            QuestionDTO questionDTO = new QuestionDTO();
            // 将两个对象中相同字段的属性值自动拷贝到另一个实体对象中去
            BeanUtils.copyProperties(question, questionDTO);
            User user = userService.findUserById(question.getCreator());
            questionDTO.setUser(user);
            questionDtoList.add(questionDTO);
        }
        return questionDtoList;
    }

    @Override
    public PageInfoDTO getPageInfo(int currPage, int pageSize) {
        PageInfoDTO pageInfoDTO = new PageInfoDTO(currPage, pageSize);
        return pageInfoDTO;
    }

    @Override
    public QuestionDTO getQuestionByID(int id) {

        Question question = questionMapper.selectByPrimaryKey(id);
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        User user = userService.findUserById(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    @Override
    public void addOrModifyQuestion(Question question) {
        if (question.getId() == null) {
            // 添加问题
            questionMapper.insert(question);
        } else {
            // 修改问题
            Question updateQuestion = new Question();
            updateQuestion.setGmtModify(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());
            QuestionExample example = new QuestionExample();
            example.createCriteria()
                    .andIdEqualTo(question.getId());
            question.setGmtModify(System.currentTimeMillis());
            questionMapper.updateByExampleSelective(updateQuestion, example);
        }
    }

}
