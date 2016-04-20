package com.questionnaire.survey.dao.impl;


import com.questionnaire.survey.dao.TextDAO;
import com.questionnaire.survey.entity.Text;
import com.questionnaire.common.pager.PageConfig;
import com.questionnaire.common.sql.ConnectionFactory;
import com.questionnaire.common.sql.SQLCommand;

import javax.sql.RowSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TextDAOImpl implements TextDAO {


    public boolean addText(Text text) {
        Connection conn = ConnectionFactory.getConnection();
        String sql = "INSERT INTO text(question_id, context) VALUES(?, ?)";
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, text.getQId());
            pstmt.setString(2, text.getTContent());
            return 1 == pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            SQLCommand.close(pstmt);
            SQLCommand.close(conn);
        }


    }


    public boolean delText(Long surveyId) {
        SQLCommand cmd = new SQLCommand();
        return -1 != cmd.executeSQL("delete from text where question_id in (select id from question where survey_id=" + surveyId + ")");
    }


    public Text findText(Long textId) {
        SQLCommand cmd = new SQLCommand();
        RowSet rs = cmd.queryRowSet("select * from text where id="
                + textId);
        Text text = new Text();
        try {
            if (rs.next()) {
                text.setTId(rs.getLong("id"));
                text.setQId(rs.getLong("question_id"));
                text.setTContent(rs.getString("content"));
                return text;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            SQLCommand.close(rs);
        }
        return null;
    }


    public List<Text> listAllText(Long questionId) {
        SQLCommand cmd = new SQLCommand();
        RowSet rs = cmd.queryRowSet("select * from text where question_id="
                + questionId);

        Text text;
        List<Text> list = new ArrayList<Text>();
        try {
            while (rs.next()) {
                text = new Text();
                text.setTId(rs.getLong("id"));
                text.setQId(rs.getLong("question_id"));
                text.setTContent(rs.getString("content"));
                list.add(text);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            SQLCommand.close(rs);
        }
        return null;
    }


    public List<?> doSelect(int recordStart, int sizePage, PageConfig pageConfig) {
        // TODO Auto-generated method stub
        return null;
    }

    public int getCount(PageConfig pageConfig) {
        // TODO Auto-generated method stub
        return 0;
    }
}
