package com.questionnaire.survey.dao.impl;


import com.questionnaire.survey.dao.AnswersheetDAO;
import com.questionnaire.survey.entity.Answersheet;
import com.questionnaire.common.pager.PageConfig;
import com.questionnaire.common.sql.ConnectionFactory;
import com.questionnaire.common.sql.SQLCommand;
import com.sun.rowset.CachedRowSetImpl;

import javax.servlet.http.HttpServletRequest;
import javax.sql.rowset.CachedRowSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnswersheetDAOImpl implements AnswersheetDAO {
    private List list_answersheet = null;

    public boolean addAnswersheet(Answersheet answersheet) {
        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement pstmt = null;
        String sql = "INSERT INTO answer_sheet(survey_id, result, post_date, user_ip) VALUES(?, ?, ?,?)";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, answersheet.getSurvey());
            pstmt.setString(2, answersheet.getAsResult());
            pstmt.setDate(3, new java.sql.Date(answersheet.getAsPostdate().getTime()));
            pstmt.setString(4, answersheet.getAsUserIp());
            return 1 == pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            SQLCommand.close(pstmt);
            SQLCommand.close(conn);
        }
    }


    public boolean delAnswersheet(Long answersheetId) {
        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement pstmt = null;
        try {
            String sql = "delete from answer_sheet where id= ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, answersheetId);
            return -1 != pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            SQLCommand.close(pstmt);
            SQLCommand.close(conn);
        }
        return false;
    }

    public Answersheet findAnswersheet(Long answersheetId) {
        Answersheet answersheet = new Answersheet();
        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement pstmt = null;
        String sql = "select * from answer_sheet where id = ?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, answersheetId);
            CachedRowSet rs = new CachedRowSetImpl();
            rs.populate(pstmt.executeQuery());
            if (rs.next()) {
                answersheet.setAsId(rs.getLong("id"));
                answersheet.setSurvey(rs.getLong("survey_id"));
                answersheet.setAsResult(rs.getString("result"));
                answersheet.setAsPostdate(rs.getDate("post_date"));
                answersheet.setAsUserIp(rs.getString("user_ip"));
            }
            return answersheet;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            SQLCommand.close(pstmt);
            SQLCommand.close(conn);
        }

    }


    public List<Answersheet> listAllAnswersheet(Long surveyId) {
        Answersheet answersheet;
        List<Answersheet> list = new ArrayList<Answersheet>();
        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement pstmt = null;
        String sql = "select * from answer_sheet where survey_id= ? order by post_date desc";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, surveyId);
            CachedRowSet rs = new CachedRowSetImpl();
            rs.populate(pstmt.executeQuery());
            while (rs.next()) {
                answersheet = new Answersheet();
                answersheet.setAsId(rs.getLong("id"));
                answersheet.setSurvey(rs.getLong("survey_id"));
                answersheet.setAsResult(rs.getString("result"));
                answersheet.setAsPostdate(rs.getDate("post_date"));
                answersheet.setAsUserIp(rs.getString("user_ip"));
                list.add(answersheet);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            SQLCommand.close(pstmt);
            SQLCommand.close(conn);
        }
        return null;
    }


    public List<?> doSelect(int recordStart, int sizePage, PageConfig pageConfig) {
        List<Answersheet> newlist = new ArrayList<Answersheet>();
        HttpServletRequest request = pageConfig.getRequest();
        List<Answersheet> list_answersheet = this.listAllAnswersheet(Long.valueOf(request.getParameter("sid")));
        for (int i = recordStart; i < recordStart + sizePage; i++) {
            if (i < list_answersheet.size())
                newlist.add(list_answersheet.get(i));
            else
                break;
        }
        return newlist;
    }


    public int getCount(PageConfig pageConfig) {
        if (this.list_answersheet == null) {
            HttpServletRequest request = pageConfig.getRequest();
            list_answersheet = this.listAllAnswersheet(Long.valueOf(request.getParameter("sid")));
        }
        return list_answersheet.size();
    }


    public boolean delAnswersheets(Long sid) {
        SQLCommand cmd = new SQLCommand();
        return -1 != cmd.executeSQL("delete from answer_sheet where survey_id="
                + sid);
    }


    public boolean isIpRepeat(Long surveyId, String ip) {
        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement pstmt = null;
        try {
            String sql = "select id from answer_sheet where survey_id= ? and user_ip= ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, surveyId);
            pstmt.setString(2, ip);
            CachedRowSet rs = new CachedRowSetImpl();
            rs.populate(pstmt.executeQuery());
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            SQLCommand.close(pstmt);
            SQLCommand.close(conn);
        }
    }
}
