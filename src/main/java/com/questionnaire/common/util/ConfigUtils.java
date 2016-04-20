package com.questionnaire.common.util;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

/**
 * 取系统配置属性
 */
public final class ConfigUtils {

    private static String DEFAULT_CONFIG_FILE = "/resources.properties";

    private static Map<String, String> propertiesMap;

    static {
        try {
            initProperty();
        } catch (IOException e) {
        }
    }

    /**
     * 初始化配置文件(config.properties)
     *
     * @throws IOException
     */
    private static void initProperty() throws IOException {
        if (propertiesMap != null) {
            return;
        }
        loadDefaultProperty();
    }

    private static void loadDefaultProperty() throws IOException {
        propertiesMap = new HashMap<String, String>();
        InputStream ins = null;
        Properties properties = new Properties();
        try {
            ins = ConfigUtils.class.getResourceAsStream(DEFAULT_CONFIG_FILE);
            if (ins == null) {
                return;
            }
            properties.load(ins);
        } finally {
            IOUtils.closeQuietly(ins);
        }
        Set<Entry<Object, Object>> entrySet = properties.entrySet();
        for (Entry<Object, Object> entry : entrySet) {
            propertiesMap.put((String) entry.getKey(), ((String) entry.getValue()).trim());
        }
    }

    private static final String getWebRoot() {

        URL url = ConfigUtils.class.getResource("/");
        String path = url.getPath();
        if (path.endsWith("/WEB-INF/classes/")) {
            int beginIndex = path.length() - "WEB-INF/classes/".length();
            return path.substring(0, beginIndex);
        }
        return path;
    }

    public static String getString(String proKey) {
        return propertiesMap.get(proKey);
    }

    public static String getString(String proKey, String defaultValue) {
        String value = propertiesMap.get(proKey);
        return StringUtils.isNullOrEmpty(value) ? defaultValue : value;
    }

    public static final int getInt(String key) {
        return Integer.parseInt(propertiesMap.get(key));
    }

    public static final int getInt(String key, int df) {
        if (propertiesMap.get(key) == null) {
            return df;
        }
        return Integer.parseInt(propertiesMap.get(key));
    }

    public static final long getLong(String key) {
        return Long.parseLong(propertiesMap.get(key));
    }

    private static final boolean getBoolean(String key, boolean defaultValue) {
        String str = getString(key, new Boolean(defaultValue).toString());
        if (str.equalsIgnoreCase("true") || str.equals("1") || str.equals("是") || str.equalsIgnoreCase("yes"))
            return Boolean.TRUE;
        return Boolean.FALSE;
    }

    /**
     * 得到网站地址
     */
    public static final String WEBSITE = getString("web.site", "");

    /**
     * 得到网站域名
     */
    public static final String DOMAIN = getString("web.domain");

    /**
     * 网站的绝对路径
     */
    public static final String WEB_ROOT = getWebRoot();

    public static final String MYSQL_DRIVER = getString("mysql.driver");
    public static final String MYSQL_URL = getString("mysql.url");
    public static final String MYSQL_USER = getString("mysql.user");
    public static final String MYSQL_PASSWORD = getString("mysql.password");

}
