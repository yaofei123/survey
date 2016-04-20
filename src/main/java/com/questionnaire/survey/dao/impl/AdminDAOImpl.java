package com.questionnaire.survey.dao.impl;


import com.questionnaire.survey.dao.AdminDAO;
import com.questionnaire.survey.entity.Admin;
import com.questionnaire.common.sql.ConnectionFactory;
import com.questionnaire.common.sql.SQLCommand;

import javax.sql.RowSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminDAOImpl implements AdminDAO {


    public boolean addAdmin(Admin admin) {
        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement pstmt = null;
        String sql = "insert into user(username,password) values(?,?)";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, admin.getUserName());
            pstmt.setString(2, admin.getPassword());
            return 1 == pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            SQLCommand.close(pstmt);
            SQLCommand.close(conn);
        }

    }

    public boolean checkPwd(String username, String pwd) {
        SQLCommand cmd = new SQLCommand();
        String realPwd = cmd.queryScalar("select password from user where username='" + username + "'");
        return pwd.equals(realPwd);
    }


    public boolean delAdmin(long id) {
        SQLCommand cmd = new SQLCommand();
        int ret = cmd.executeSQL("delete from user where id=" + id);
        return ret == 1;
    }

    public Admin findAdmin(long id) {
        SQLCommand cmd = new SQLCommand();
        RowSet rs = cmd.queryRowSet("select * from user where id=" + id);
        try {
            if (rs.next()) {
                return getUser(rs);
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Admin findAdmin(String username) {
        SQLCommand cmd = new SQLCommand();
        RowSet rs = cmd.queryRowSet("select * from user where username='" + username + "'");
        try {
            if (rs.next()) {
                return getUser(rs);
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Admin convertAdmin(RowSet rs) {
        try {
            if (rs.next()) {
                return getUser(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List listAllAdmin() {
        SQLCommand cmd = new SQLCommand();
        RowSet rs = cmd.queryRowSet("select * from user");
        List<Admin> list = new ArrayList<Admin>();
        try {
            while (rs.next()) {
                list.add(getUser(rs));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Admin getUser(RowSet rs) throws SQLException {
        Admin admin = new Admin();
        admin.setId(rs.getLong("id"));
        admin.setUserName(rs.getString("username"));
        admin.setPassword(rs.getString("password"));
        return admin;
    }

    public boolean updateAdmin(Admin admin) {
        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement pstmt = null;
        String sql = "UPDATE user set username=?,password=? where id=?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, admin.getUserName());
            pstmt.setString(2, admin.getPassword());
            pstmt.setLong(3, admin.getId());
            return 1 == pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            SQLCommand.close(pstmt);
            SQLCommand.close(conn);
        }
    }


}
