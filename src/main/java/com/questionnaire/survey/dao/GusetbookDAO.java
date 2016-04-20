package com.questionnaire.survey.dao;


import com.questionnaire.survey.entity.Guestbook;
import com.questionnaire.common.pager.PageListener;

import java.util.List;

public interface GusetbookDAO extends PageListener {
    boolean addGusetbook(Guestbook guestbook);

    boolean updateGuestbook(Guestbook guestbook);

    boolean delGuestbook(Long guestbookId);

    List<Guestbook> listAllGuestbook();

    Guestbook findGuestbook(Long guestbookId);
}
