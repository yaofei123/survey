package com.questionnaire.survey.dao;


import com.questionnaire.survey.entity.Config;

public interface ConfigDAO {

    boolean updateConfig(Config config);

    Config findConfig();
}
