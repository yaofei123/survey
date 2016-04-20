package com.questionnaire.survey.dao.impl;


import com.questionnaire.common.pager.PageConfig;
import com.questionnaire.common.sql.ConnectionFactory;
import com.questionnaire.common.sql.SQLCommand;
import com.questionnaire.survey.dao.QuestionDAO;
import com.questionnaire.survey.entity.Question;

import javax.servlet.http.HttpServletRequest;
import javax.sql.RowSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class QuestionDAOImpl implements QuestionDAO {

    private List<Question> list_question = null;

    public static void main(String[] args) {
        Question q = new Question();
        q.setQId(28L);
        QuestionDAOImpl dao = new QuestionDAOImpl();
        dao.updateQuestion(q);
    }

    public boolean addQuestion(Question question) {
        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement pstmt = null;
        String sql = "INSERT INTO question(survey_id, type,head, body, result, img, jdtz, orders) VALUES(?, ?, ?,?, ?,?,?,?)";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, question.getSurvey());
            pstmt.setLong(2, question.getQType());
            pstmt.setString(3, question.getQHead());
            pstmt.setString(4, question.getQBody());
            pstmt.setString(5, question.getQResult());
            pstmt.setString(6, question.getQImg());
            pstmt.setString(7, question.getQJdtz());
            pstmt.setLong(8, question.getQOrder());
            return 1 == pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            SQLCommand.close(pstmt);
            SQLCommand.close(conn);
        }
    }

    public boolean delQuestion(Long questionId) {
        SQLCommand cmd = new SQLCommand();
        return -1 != cmd.executeSQL("delete from question where id="
                + questionId);
    }

    public Question findQuestion(Long questionId) {
        SQLCommand cmd = new SQLCommand();
        RowSet rs = cmd.queryRowSet("select * from question where id="
                + questionId);

        Question question = new Question();
        try {
            if (rs.next()) {
                question.setSurvey(rs.getLong("survey_id"));
                question.setQId(rs.getLong("id"));
                question.setQType(rs.getLong("type"));
                question.setQHead(rs.getString("head"));
                question.setQBody(rs.getString("body"));
                question.setQResult(rs.getString("result"));
                question.setQImg(rs.getString("img"));
                question.setQJdtz(rs.getString("jdtz"));
                question.setQOrder(rs.getLong("orders"));
            }
            return question;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    public List<Question> listAllQuestion(Long surveyId) {
        return this.listAllQuestion(surveyId, "asc");
    }

    public boolean updateQuestion(Question question) {
        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement pstmt = null;
        String sql = "UPDATE question SET survey_id=?, type=?, head=?, body=?, result=?,img=?, jdtz=?, orders =? WHERE id=?";
        try {
            pstmt = conn.prepareStatement(sql);
            //System.out.println(question.getSurvey());
            pstmt.setLong(1, question.getSurvey());
            pstmt.setLong(2, question.getQType());
            pstmt.setString(3, question.getQHead());
            pstmt.setString(4, question.getQBody());
            pstmt.setString(5, question.getQResult());
            pstmt.setString(6, question.getQImg());
            pstmt.setString(7, question.getQJdtz());
            pstmt.setLong(8, question.getQOrder());
            pstmt.setLong(9, question.getQId());
            return 1 == pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            SQLCommand.close(pstmt);
            SQLCommand.close(conn);
        }
    }

    public List<?> doSelect(int recordStart, int sizePage, PageConfig pageConfig) {
        List<Question> newlist = new ArrayList<Question>();
        HttpServletRequest request = pageConfig.getRequest();
        Long surveyId = Long.valueOf(request.getParameter("sid"));
        if (this.list_question == null)
            list_question = this.listAllQuestion(surveyId, "desc");
        for (int i = recordStart; i < recordStart + sizePage; i++) {
            if (i < list_question.size())
                newlist.add(list_question.get(i));
            else
                break;
        }
        return newlist;
    }

    public int getCount(PageConfig pageConfig) {
        HttpServletRequest request = pageConfig.getRequest();
        Long surveyId = Long.valueOf(request.getParameter("sid"));
        if (this.list_question == null)
            list_question = this.listAllQuestion(surveyId);

        return list_question.size();
    }

    public boolean delQuestions(Long surveyId) {
        SQLCommand cmd = new SQLCommand();
        return -1 != cmd.executeSQL("delete from question where survey_id="
                + surveyId);

    }

    public List<Question> listAllQuestion(Long surveyId, String ascORdesc) {
        SQLCommand cmd = new SQLCommand();
        RowSet rs = cmd.queryRowSet("select * from question where survey_id="
                + surveyId + " order by id " + ascORdesc);
        List<Question> list = new ArrayList<Question>();
        try {
            while (rs.next()) {
                list.add(getQuestion(rs));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Question> listQuestions(String WhereClause) {
        SQLCommand cmd = new SQLCommand();
        RowSet rs = cmd.queryRowSet("select * from question where " + WhereClause);
        List<Question> list = new ArrayList<Question>();
        try {
            while (rs.next()) {
                list.add(getQuestion(rs));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    private Question getQuestion(RowSet rs) throws SQLException {
        Question question = new Question();
        question.setQId(rs.getLong("id"));
        question.setSurvey(rs.getLong("survey_id"));
        question.setQType(rs.getLong("type"));
        question.setQHead(rs.getString("head"));
        question.setQBody(rs.getString("body"));
        question.setQResult(rs.getString("result"));
        question.setQImg(rs.getString("img"));
        question.setQJdtz(rs.getString("jdtz"));
        question.setQOrder(rs.getLong("orders"));
        return question;
    }
}
