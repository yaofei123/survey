package com.questionnaire.survey.dao;


import com.questionnaire.survey.entity.Question;
import com.questionnaire.common.pager.PageListener;

import java.util.List;

public interface QuestionDAO extends PageListener {
    /**
     * ��������
     *
     * @param question
     * @return
     */
    boolean addQuestion(Question question);

    /**
     * �༭����������
     *
     * @param question
     * @return
     */
    boolean updateQuestion(Question question);

    /**
     * ɾ������
     *
     * @param question
     * @return
     */
    boolean delQuestion(Long questionId);

    /**
     * ɾ��ָ���ʾ����������
     *
     * @param question
     * @return
     */
    boolean delQuestions(Long surveyId);

    /**
     * �г�ָ���������������,����ָ����ID�������������
     *
     * @return
     */
    List<Question> listQuestions(String WhereClause);

    List<Question> listAllQuestion(Long surveyId);

    List<Question> listAllQuestion(Long surveyId, String ascORdesc);

    /**
     * ����ָ��ID������
     *
     * @param question
     * @return
     */
    Question findQuestion(Long questionId);
}
