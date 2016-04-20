package com.questionnaire.survey.dao;


import com.questionnaire.survey.entity.Link;
import com.questionnaire.common.pager.PageListener;

import java.util.List;

public interface LinkDAO extends PageListener {
    boolean addLink(Link link);

    boolean updateLink(Link link);

    boolean delLink(Long linkId);

    List<Link> listAllLink();

    Link findLink(Long linkId);
}
