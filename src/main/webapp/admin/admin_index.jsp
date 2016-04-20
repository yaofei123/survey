<%@ page language="java" import="com.questionnaire.survey.dao.ConfigDAO" pageEncoding="UTF-8" %>
<%@ page import="com.questionnaire.survey.dao.DAOFactory" %>
<%@ page import="com.questionnaire.survey.entity.Config" %>
<%
    ConfigDAO configdao = DAOFactory.getConfigDAO();
    Config cfg = configdao.findConfig();
%>
<html>
<head>

    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title><%=cfg.getCSiteName() %>-后台管理</title>
</head>
<frameset rows="63,*,32" cols="*" frameborder="no" border="0" framespacing="0">
    <frame src="admin_top.jsp" name="topFrame" scrolling="No" noresize="noresize" id="topFrame" title="topFrame"/>
    <frameset rows="*" cols="177,*" framespacing="0" frameborder="no" border="0">
        <frame src="admin_left.jsp" name='left' scrolling='yes' noresize='noresize'/>
        <frame src="admin_main.jsp" name='right' scrolling='yes' noresize='noresize'/>
    </frameset>

</frameset>
</html>