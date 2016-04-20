package com.questionnaire.common.sql;

import com.questionnaire.common.util.ConfigUtils;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {
//    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionFactory.class);

    private ConnectionFactory() {
    }

    /**
     * @return
     */
    public static Connection getConnection() {
        Connection con = null;  //创建用于连接数据库的Connection对象
        String mysqlDriver = ConfigUtils.MYSQL_DRIVER;
        String mysqlUrl = ConfigUtils.MYSQL_URL;
        String mysqlUser = ConfigUtils.MYSQL_USER;
        String mysqlPassword = ConfigUtils.MYSQL_PASSWORD;
        try {
            Class.forName(mysqlDriver).newInstance();// 加载Mysql数据驱动
            con = DriverManager.getConnection(mysqlUrl, mysqlUser, mysqlPassword);// 创建数据连接
        } catch (Exception e) {
            System.out.println(e);
//            LOGGER.error("数据库连接失败" + e);
        }
        return con; //返回所建立的数据库连接
    }
}
