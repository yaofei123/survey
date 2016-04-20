package com.questionnaire.survey.dao;


import com.questionnaire.survey.entity.Answersheet;
import com.questionnaire.common.pager.PageListener;

import java.util.List;

public interface AnswersheetDAO extends PageListener {

    boolean addAnswersheet(Answersheet answersheet);

    //boolean updateAnswersheet(Answersheet answersheet);
    boolean delAnswersheet(Long answersheetId);

    boolean delAnswersheets(Long sid);

    List<Answersheet> listAllAnswersheet(Long surveyId);

    Answersheet findAnswersheet(Long answersheetId);

    boolean isIpRepeat(Long sid, String IP);
}
