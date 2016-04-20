package com.questionnaire.survey.dao;

import com.questionnaire.survey.entity.Text;
import com.questionnaire.common.pager.PageListener;

import java.util.List;

public interface TextDAO extends PageListener {
    boolean addText(Text text);

    boolean delText(Long sid);

    List<Text> listAllText(Long questionId);

    Text findText(Long textId);
}
