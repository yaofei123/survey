package com.questionnaire.survey.dao;


import com.questionnaire.survey.entity.Templet;
import com.questionnaire.common.pager.PageListener;

import java.util.List;

public interface TempletDAO extends PageListener {

    boolean addTemplet(Templet templet);

    boolean updateTemplet(Templet templet);

    boolean delTemplet(Long templetId);

    List<Templet> listAllTemplet();

    Templet findTemplet(Long templetId);
}
