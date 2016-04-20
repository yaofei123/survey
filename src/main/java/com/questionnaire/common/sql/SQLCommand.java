package com.questionnaire.common.sql;

import com.sun.rowset.CachedRowSetImpl;

import javax.sql.RowSet;
import javax.sql.rowset.CachedRowSet;
import java.sql.*;

public class SQLCommand {
    private String JNDIname = "";

    public SQLCommand() {
    }

    public SQLCommand(String JNDIname) {
        this.JNDIname = JNDIname;
    }

    public static void close(Connection conn) {
        if (conn != null)
            try {
                conn.close();
            } catch (SQLException e) {

                e.printStackTrace();
            }
    }

    public static void close(Statement stmt) {
        if (stmt != null)
            try {
                stmt.close();
            } catch (SQLException e) {

                e.printStackTrace();
            }
    }

    public static void close(PreparedStatement pstmt) {
        if (pstmt != null)
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    public static void close(ResultSet rs) {
        if (rs != null)
            try {
                rs.close();
            } catch (SQLException e) {

                e.printStackTrace();
            }
    }

    private Connection getConn() {
        return ConnectionFactory.getConnection();
    }

    /**
     * ִ��һ��SQL������䡣
     *
     * @param sql
     * @return
     */
    public boolean executeSQLs(String[] sql) {
        Connection conn = this.getConn();
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            for (int i = 0; i < sql.length; i++) {
                stmt.addBatch(sql[i]);
            }
            int[] ret = stmt.executeBatch();
            for (int i = 0; i < ret.length; i++) {
                if (ret[i] == -1)
                    return false;
            }
            return true;
        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        } finally {
            SQLCommand.close(stmt);
            SQLCommand.close(conn);

        }

    }

    /**
     * ִ��һ��SQL�������
     *
     * @param sql
     * @return
     */
    public int executeSQL(String sql) {
        Connection conn = this.getConn();
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            return stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        } finally {
            SQLCommand.close(stmt);
            SQLCommand.close(conn);

        }

    }

    /**
     *
     * @param sql
     * @return
     */
    public RowSet queryRowSet(String sql) {
        Connection conn = this.getConn();
        ResultSet rs = null;
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            CachedRowSet crs = new CachedRowSetImpl();
            crs.populate(rs);
            return crs;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            SQLCommand.close(rs);
            SQLCommand.close(stmt);
            SQLCommand.close(conn);

        }


    }

    /**
     * ��ȡһ��SQL�Ľ��������ĵ�һ�е�һ�е��ַ���
     *
     * @param sql
     * @return
     */
    public String queryScalar(String sql) {

        RowSet rs = this.queryRowSet(sql);

        try {
            if (!rs.next())
                return "";
            else {
                return new String(rs.getObject(1).toString());
            }
        } catch (SQLException e) {

            e.printStackTrace();
            return "";
        }
    }


}
