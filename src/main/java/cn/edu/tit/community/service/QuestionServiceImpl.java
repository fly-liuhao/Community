package cn.edu.tit.community.service;

import cn.edu.tit.community.dto.CommentDTO;
import cn.edu.tit.community.dto.PageInfoDTO;
import cn.edu.tit.community.dto.QuestionDTO;
import cn.edu.tit.community.dto.SearchQuestionDTO;
import cn.edu.tit.community.enums.CustomizeErrorCodeEnum;
import cn.edu.tit.community.exception.CustomizeException;
import cn.edu.tit.community.mapper.QuestionExtMapper;
import cn.edu.tit.community.mapper.QuestionMapper;
import cn.edu.tit.community.mapper.UserMapper;
import cn.edu.tit.community.model.Question;
import cn.edu.tit.community.model.QuestionExample;
import cn.edu.tit.community.model.User;
import cn.edu.tit.community.util.TimeFormat;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    QuestionExtMapper questionExtMapper;

    @Autowired
    UserMapper userMapper;



    @Override
    public int findQuestionCountByCreator(int creator) {
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(creator);
        return (int) questionMapper.countByExample(questionExample);
    }


    @Override
    public List<QuestionDTO> findQuestionByCreator(int offset, Integer pageSize, int creator) {
        // 获取分页后的问题集合
        QuestionExample example = new QuestionExample();
        example.setOrderByClause("gmt_modify desc");
        example.createCriteria().andCreatorEqualTo(creator);
        List<Question> questionList = questionMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, pageSize));
        // 用于传输时的问题集合（补充了创建问题的用户信息）
        List<QuestionDTO> questionDtoList = new ArrayList<QuestionDTO>();
        for (Question question : questionList) {
            QuestionDTO questionDTO = new QuestionDTO();
            // 将两个对象中相同字段的属性值自动拷贝到另一个实体对象中去
            BeanUtils.copyProperties(question, questionDTO);
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            questionDTO.setUser(user);
            questionDTO.setPubtime(TimeFormat.timeFormat(question.getGmtCreate()));
            questionDtoList.add(questionDTO);
        }
        return questionDtoList;
    }

    @Override
    public int findQuestionCountByTitle(String keyword) {
        String regexpKeyword = keyword.trim().replaceAll(" +", "|");
        return  questionExtMapper.selectQuestionCountByTitle(regexpKeyword);
    }

    @Override
    public List<QuestionDTO> findQuestionByTitle(SearchQuestionDTO searchQuestionDTO) {
        // 获取分页后的问题集合
        List<Question> questionList = questionExtMapper.selectQuestionByTitle(searchQuestionDTO);
        // 用于传输时的问题集合（补充了创建问题的用户信息）
        List<QuestionDTO> questionDtoList = new ArrayList<QuestionDTO>();
        for (Question question : questionList) {
            QuestionDTO questionDTO = new QuestionDTO();
            // 将两个对象中相同字段的属性值自动拷贝到另一个实体对象中去
            BeanUtils.copyProperties(question, questionDTO);
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            questionDTO.setUser(user);
            questionDTO.setPubtime(TimeFormat.timeFormat(question.getGmtCreate()));
            questionDtoList.add(questionDTO);
        }
        return questionDtoList;
    }

    @Override
    public QuestionDTO getQuestionByID(int id) {

        Question question = questionMapper.selectByPrimaryKey(id);
        if (question == null) {
            throw new CustomizeException(CustomizeErrorCodeEnum.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    @Override
    public void addOrModifyQuestion(Question question) {
        if (question.getId() == null) {
            // 添加问题
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModify(question.getGmtCreate());
            question.setCommentCount(0);
            question.setViewCount(0);
            question.setLikeCount(0);
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
            int updateResult = questionMapper.updateByExampleSelective(updateQuestion, example);
            if (updateResult == 0) {
                throw new CustomizeException(CustomizeErrorCodeEnum.QUESTION_NOT_FOUND);
            }
        }
    }

    @Override
    public void incViewCount(int id, int step) {
        int updateResult = questionExtMapper.incViewCount(id, step);
        if (updateResult == 0) {
            throw new CustomizeException(CustomizeErrorCodeEnum.QUESTION_NOT_FOUND);
        }
    }

    @Override
    public List<QuestionDTO> getSimilarQuestion(QuestionDTO srcQuestionDTO) {
        if (StringUtils.isBlank(srcQuestionDTO.getTag())) {
            return new ArrayList<>();
        }
        // 将标签中的逗号（包含中英文）替换成"|"，用于数据库正则表达式模糊查询
        String regexpTag = srcQuestionDTO.getTag().replaceAll(",|，", "|");

        Question question = new Question();
        question.setId(srcQuestionDTO.getId());
        question.setTag(regexpTag);

        List<Question> questionList = questionExtMapper.selectSimilarQuestion(question);

        // 使用λ表达式
        List<QuestionDTO> questionDTOS = questionList.stream().map(q -> {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(q, questionDTO);
            return questionDTO;
        }).collect(Collectors.toList());

        return questionDTOS;
    }
}
