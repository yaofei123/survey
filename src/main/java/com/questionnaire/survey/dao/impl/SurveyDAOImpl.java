package com.questionnaire.survey.dao.impl;

import com.questionnaire.common.pager.PageConfig;
import com.questionnaire.common.sql.ConnectionFactory;
import com.questionnaire.common.sql.SQLCommand;
import com.questionnaire.survey.dao.SurveyDAO;
import com.questionnaire.survey.entity.Survey;

import javax.sql.RowSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SurveyDAOImpl implements SurveyDAO {
    private List<Survey> list_survey = null;


    public boolean addSurvey(Survey survey) {
        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement pstmt = null;
        String sql = "INSERT INTO survey(templet_id, name, describer, author,img, ip_repeat, create_date, ip_limit_type, ip_range,password,is_open, expire_date, is_audited, hits, use_hits) VALUES(?, ?, ?, ?, ?, ?,?, ?,?, ?, ?, ?, ?,?, ?)";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, survey.getTemplet());
            pstmt.setString(2, survey.getSName());
            pstmt.setString(3, survey.getSDesc());
            pstmt.setString(4, survey.getSAuthor());
            pstmt.setString(5, survey.getSImg());
            pstmt.setBoolean(6, survey.getSIpRepeat());
            pstmt.setDate(7, new java.sql.Date(survey.getSCreateDate().getTime()));
            pstmt.setString(8, survey.getSIpLimitType());
            pstmt.setString(9, survey.getSIpRange());
            pstmt.setString(10, survey.getSPassword());
            pstmt.setBoolean(11, survey.getSIsOpen());
            pstmt.setDate(12, new java.sql.Date(survey.getSExpireDate().getTime()));
            pstmt.setBoolean(13, survey.getSIsAudited());
            pstmt.setLong(14, survey.getSHits());
            pstmt.setLong(15, survey.getSUsehits());
            return 1 == pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            SQLCommand.close(pstmt);
            SQLCommand.close(conn);
        }
    }

    public boolean delSurvey(Long surveyId) {
        SQLCommand cmd = new SQLCommand();
        return -1 != cmd.executeSQL("delete from survey where id=" + surveyId);
    }

    public Survey findSurvey(Long surveyId) {
        SQLCommand cmd = new SQLCommand();
        RowSet rs = cmd.queryRowSet("select * from survey where id=" + surveyId);
        try {
            if (rs.next()) {
                return getSurvey(rs);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            SQLCommand.close(rs);
        }

    }


    public List<Survey> listAllSurvey(String order) {
        SQLCommand cmd = new SQLCommand();
        RowSet rs = cmd.queryRowSet("select * from survey order by " + order);
        List<Survey> list = new ArrayList<Survey>();
        try {
            while (rs.next()) {
                list.add(getSurvey(rs));
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean updateSurvey(Survey survey) {
        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement pstmt = null;
        String sql = "UPDATE survey SET templet_id=?, name=?, describer=?, author=?, img=?, ip_repeat=?, create_date=?, ip_limit_type=?,ip_range=?, password=?, is_open=?, expire_date=?, is_audited=?, hits=?, use_hits=? WHERE id=?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, survey.getTemplet());
            pstmt.setString(2, survey.getSName());
            pstmt.setString(3, survey.getSDesc());
            pstmt.setString(4, survey.getSAuthor());
            pstmt.setString(5, survey.getSImg());
            pstmt.setBoolean(6, survey.getSIpRepeat());
            pstmt.setDate(7, new java.sql.Date(survey.getSCreateDate().getTime()));
            pstmt.setString(8, survey.getSIpLimitType());
            pstmt.setString(9, survey.getSIpRange());
            pstmt.setString(10, survey.getSPassword());
            pstmt.setBoolean(11, survey.getSIsOpen());
            pstmt.setDate(12, new java.sql.Date(survey.getSExpireDate().getTime()));
            pstmt.setBoolean(13, survey.getSIsAudited());
            pstmt.setLong(14, survey.getSHits());
            pstmt.setLong(15, survey.getSUsehits());
            pstmt.setLong(16, survey.getSId());
            return 1 == pstmt.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        } finally {
            SQLCommand.close(pstmt);
            SQLCommand.close(conn);

        }

    }


    public List<Survey> doSelect(int recordStart, int sizePage, PageConfig pageConfig) {
        //System.out.println(recordStart+"   "+sizePage);
        List<Survey> newlist = new ArrayList<Survey>();
        if (this.list_survey == null) {
            if ("front_end".equals(pageConfig.getAction()))
                list_survey = this.listVisitableSurvey();
            else
                list_survey = this.listAllSurvey();
        }
        for (int i = recordStart; i < recordStart + sizePage; i++) {
            if (i < list_survey.size())
                newlist.add(list_survey.get(i));
            else
                break;

        }
        return newlist;
    }


    public int getCount(PageConfig pageConfig) {
        if (this.list_survey == null)
            list_survey = this.listAllSurvey();

        return list_survey.size();
    }


    public List<Survey> listAllSurvey() {
        return this.listAllSurvey("id desc");
    }


    public List<Survey> listVisitableSurvey() {

        return listVisitableSurvey("create_date desc,id desc");
    }


    public List<Survey> listVisitableSurvey(String order) {
        String sql = "select * from survey where is_open =1 and is_audited=1 and expire_date > '" + new java.sql.Date(new java.util.Date().getTime()) + "' order by " + order;
        //System.out.println(sql);
        SQLCommand cmd = new SQLCommand();
        RowSet rs = cmd.queryRowSet(sql);
        List<Survey> list = new ArrayList<Survey>();
        try {
            while (rs.next()) {
                list.add(getSurvey(rs));
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            SQLCommand.close(rs);
        }
    }

    private Survey getSurvey(RowSet rs) throws Exception {
        Survey survey = new Survey();
        survey.setSId(rs.getLong("id"));
        survey.setTemplet(rs.getLong("templet_id"));
        survey.setSName(rs.getString("name"));
        survey.setSDesc(rs.getString("describer"));
        survey.setSAuthor(rs.getString("author"));
        survey.setSImg(rs.getString("img"));
        survey.setSIpRepeat(rs.getBoolean("ip_repeat"));
        survey.setSCreateDate(rs.getDate("create_date"));
        survey.setSIpLimitType(rs.getString("ip_limit_type"));
        survey.setSIpRange(rs.getString("ip_range"));
        survey.setSPassword(rs.getString("password"));
        survey.setSIsOpen(rs.getBoolean("is_open"));
        survey.setSExpireDate(rs.getDate("expire_date"));
        survey.setSIsAudited(rs.getBoolean("is_audited"));
        survey.setSHits(rs.getLong("hits"));
        survey.setSUsehits(rs.getLong("use_hits"));
        return survey;
    }
}
