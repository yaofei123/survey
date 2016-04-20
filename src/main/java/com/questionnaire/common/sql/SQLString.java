package com.questionnaire.common.sql;

public class SQLString {

    public static String formatString(String str) {
        return str.replace("'", "''");
    }
}
