package com.questionnaire.survey.dao.impl;


import com.questionnaire.survey.dao.TempletDAO;
import com.questionnaire.survey.entity.Templet;
import com.questionnaire.common.pager.PageConfig;
import com.questionnaire.common.sql.ConnectionFactory;
import com.questionnaire.common.sql.SQLCommand;

import javax.sql.RowSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TempletDAOImpl implements TempletDAO {
    private List<Templet> list_templet = null;

    public boolean addTemplet(Templet templet) {
        Connection conn = ConnectionFactory.getConnection();
        String sql = "INSERT INTO templet(name, top, body,bottom, default) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, templet.getTempletName());
            pstmt.setString(2, templet.getTempletTop());
            pstmt.setString(3, templet.getTempletBody());
            pstmt.setString(4, templet.getTempletBottom());
            pstmt.setBoolean(5, templet.getTempletDefault());
            return 1 == pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean delTemplet(Long templetId) {
        Connection conn = ConnectionFactory.getConnection();
        String sql = "delete from templet where id= ?";
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, templetId);
            return -1 != pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            SQLCommand.close(pstmt);
            SQLCommand.close(conn);
        }
        return false;
    }

    public Templet findTemplet(Long templetId) {
        SQLCommand cmd = new SQLCommand();
        RowSet rs = cmd.queryRowSet("select * from templet where id="
                + templetId);
        Templet templet = new Templet();
        try {
            if (rs.next()) {
                templet.setTempletId(rs.getLong("id"));
                templet.setTempletName(rs.getString("name"));
                templet.setTempletTop(rs.getString("top"));
                templet.setTempletBody(rs.getString("body"));
                templet.setTempletBottom(rs.getString("bottom"));
                templet.setTempletDefault(rs.getBoolean("default"));
                return templet;
            }

            return null;

        } catch (SQLException e) {

            e.printStackTrace();
        }
        return null;
    }


    public List<Templet> listAllTemplet() {
        SQLCommand cmd = new SQLCommand();
        RowSet rs = cmd.queryRowSet("select * from templet");
        Templet templet;
        List<Templet> list = new ArrayList<Templet>();
        try {
            while (rs.next()) {
                templet = new Templet();
                templet.setTempletId(rs.getLong("id"));
                templet.setTempletName(rs.getString("name"));
                templet.setTempletTop(rs.getString("top"));
                templet.setTempletBody(rs.getString("body"));
                templet.setTempletBottom(rs.getString("bottom"));
                templet.setTempletDefault(rs.getBoolean("default"));
                list.add(templet);
            }

            return list;

        } catch (SQLException e) {

            e.printStackTrace();
        }
        return null;
    }


    public boolean updateTemplet(Templet templet) {
        Connection conn = ConnectionFactory.getConnection();
        String sql = "UPDATE templet SET name=?, top=?,body=?, bottom=?, default=? WHERE id=?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, templet.getTempletName());
            pstmt.setString(2, templet.getTempletTop());
            pstmt.setString(3, templet.getTempletBody());
            pstmt.setString(4, templet.getTempletBottom());
            pstmt.setBoolean(5, templet.getTempletDefault());
            pstmt.setLong(6, templet.getTempletId());
            return 1 == pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }


    public List<?> doSelect(int recordStart, int sizePage, PageConfig pageConfig) {
        List<Templet> newlist = new ArrayList<Templet>();
        if (this.list_templet == null)
            list_templet = this.listAllTemplet();
        for (int i = recordStart; i < recordStart + sizePage; i++) {
            if (i < list_templet.size())
                newlist.add(list_templet.get(i));
            else
                break;
        }
        return newlist;
    }


    public int getCount(PageConfig pageConfig) {
        if (this.list_templet == null)
            list_templet = this.listAllTemplet();
        return list_templet.size();
    }

}
