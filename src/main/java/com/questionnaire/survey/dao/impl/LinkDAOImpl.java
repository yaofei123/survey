package com.questionnaire.survey.dao.impl;

import com.questionnaire.survey.dao.LinkDAO;
import com.questionnaire.survey.entity.Link;
import com.questionnaire.common.pager.PageConfig;
import com.questionnaire.common.sql.ConnectionFactory;
import com.questionnaire.common.sql.SQLCommand;

import javax.sql.RowSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LinkDAOImpl implements LinkDAO {
    public boolean addLink(Link link) {
        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement pstmt = null;
        String sql = "INSERT INTO link(url, name, img, info, is_lock, add_time) VALUES( ?, ?, ?, ?, ?, ?)";
        try {
            pstmt = conn.prepareStatement(sql);
            //pstmt.setLong(1, survey.getTemplet());
            pstmt.setString(1, link.getLUrl());
            pstmt.setString(2, link.getLName());
            pstmt.setString(3, link.getLImg());
            pstmt.setString(4, link.getLInfo());
            pstmt.setBoolean(5, link.getLIsLock());
            pstmt.setDate(6, new java.sql.Date(link.getLAddtime().getTime()));
            return 1 == pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            SQLCommand.close(pstmt);
            SQLCommand.close(conn);
        }
    }

    public boolean delLink(Long linkId) {
        SQLCommand cmd = new SQLCommand();
        return -1 != cmd.executeSQL("delete from link where id=" + linkId);
    }

    public Link findLink(Long linkId) {
        SQLCommand cmd = new SQLCommand();
        RowSet rs = cmd.queryRowSet("select * from link where id=" + linkId);
        try {
            if (rs.next()) {
                return getLink(rs);
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Link> listAllLink() {
        SQLCommand cmd = new SQLCommand();
        RowSet rs = cmd.queryRowSet("select * from link");
        List<Link> list = new ArrayList<Link>();
        Link link;
        try {
            while (rs.next()) {
                list.add(getLink(rs));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    public boolean updateLink(Link link) {
        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement pstmt = null;
        String sql = "	UPDATE link SET url=?, name=?, img=?, info=?, is_lock=? WHERE id=?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, link.getLUrl());
            pstmt.setString(2, link.getLName());
            pstmt.setString(3, link.getLImg());
            pstmt.setString(4, link.getLInfo());
            pstmt.setBoolean(5, link.getLIsLock());
            pstmt.setLong(6, link.getLId());
            //	pstmt.setDate(6, new java.sql.Date(link.getLAddtime().getTime()));
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
        // TODO Auto-generated method stub
        return null;
    }

    public int getCount(PageConfig pageConfig) {
        // TODO Auto-generated method stub
        return 0;
    }

    private Link getLink(RowSet rs) throws SQLException {
        Link link = new Link();
        link.setLId(rs.getLong("id"));
        link.setLName(rs.getString("name"));
        link.setLUrl(rs.getString("url"));
        link.setLImg(rs.getString("img"));
        link.setLInfo(rs.getString("info"));
        link.setLIsLock(rs.getBoolean("is_lock"));
        link.setLAddtime(rs.getDate("add_time"));
        return link;
    }
}
