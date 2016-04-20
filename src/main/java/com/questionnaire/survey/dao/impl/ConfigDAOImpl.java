package com.questionnaire.survey.dao.impl;


import com.questionnaire.survey.dao.ConfigDAO;
import com.questionnaire.survey.entity.Config;
import com.questionnaire.common.sql.ConnectionFactory;
import com.questionnaire.common.sql.SQLCommand;

import javax.sql.RowSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConfigDAOImpl implements ConfigDAO {

    public boolean updateConfig(Config config) {
        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement pstmt = null;
        String sql = "UPDATE config SET site_name=?, site_url=?, is_open=?, close_word=? ,copyright=? WHERE id=1";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, config.getCSiteName());
            pstmt.setString(2, config.getCSiteUrl());
            pstmt.setInt(3, config.getCIsOpen() ? 0 : 1);
            pstmt.setString(4, config.getCCloseWord());
            pstmt.setString(5, config.getCopyright());
            return 1 == pstmt.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        } finally {
            SQLCommand.close(pstmt);
            SQLCommand.close(conn);
        }

    }


    public Config findConfig() {
        SQLCommand cmd = new SQLCommand();
        RowSet rs = cmd.queryRowSet("select * from config where id=1");
        Config config = new Config();
        try {
            if (rs.next()) {
                config.setCSiteName(rs.getString("site_name"));
                config.setCSiteUrl(rs.getString("site_url"));
                config.setCIsOpen(0 == rs.getInt("is_open"));
                config.setCCloseWord(rs.getString("close_word"));
                config.setCopyright(rs.getString("copyright"));
                return config;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
