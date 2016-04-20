package com.questionnaire.survey.dao;


import com.questionnaire.survey.dao.impl.*;

public class DAOFactory {

    private DAOFactory() {

    }

    public static SurveyDAO getSurveyDAO() {
        return new SurveyDAOImpl();
    }

    public static TempletDAO getTempletDAO() {
        return new TempletDAOImpl();
    }

    public static QuestionDAO getQuestionDAO() {
        return new QuestionDAOImpl();
    }

    public static TextDAO getTextDAO() {
        return new TextDAOImpl();
    }

    public static ConfigDAO getConfigDAO() {
        return new ConfigDAOImpl();
    }

    public static AnswersheetDAO getAnswersheetDAO() {
        return new AnswersheetDAOImpl();
    }

    public static AdminDAO getAdminDAO() {
        return new AdminDAOImpl();
    }

    public static LinkDAO getLinkDAO() {
        return new LinkDAOImpl();
    }
}
