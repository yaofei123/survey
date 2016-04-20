<%@ page language="java" import="com.questionnaire.survey.dao.AdminDAO" pageEncoding="UTF-8" %>
<%@ page import="com.questionnaire.survey.dao.DAOFactory" %>
<%@ page import="com.questionnaire.survey.entity.Admin" %>
<%@ page import="java.util.List" %>
<jsp:useBean id="pageConfig" class="com.questionnaire.common.pager.PageConfig"/>
<jsp:setProperty property="request" name="pageConfig" value="<%=request %>"/>
<%
    String username = (String) session.getAttribute("username");
    if (!"admin".equals(username)) {
        out.println("您权限不足！");
        return;
    }
    AdminDAO dao = DAOFactory.getAdminDAO();
//PageControl pc=new PageControl(dao,pageConfig,"SurveyAdmin.jsp");
    //pc.setSizePage(20);

    List<Admin> aList = dao.listAllAdmin();


%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

    <title>My JSP 'SurveyAudi.jsp' starting page</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">

    <link rel="stylesheet" type="text/css" href="../Css/Admin.css">
    <script language="JavaScript" src="../Js/Func.js"></script>
    <script language="javascript">window.onload = tableFix;</script>
    <script type="text/javascript">
        function DelAdmin(aid) {
            if (confirm("确定要删除这个用户吗？") == true)
                window.location = "../servlet/AdminManage.do?op=DelAdmin&aid=" + aid;
        }
    </script>
</head>

<body>
<div class=nav><a href=admin_main.jsp>桌面</a>»管理员列表
    <hr>
</div>
<table class=table cellspacing="0" cellpadding="0" align="center">
    <tbody>
    <tr>
        <th width=7%>编号</th>
        <th width=20%>用户名</th>
        <th width=15%>密码 <br></th>
        <th>操作 <br></th>
    </tr>
    <%
        for (Admin admin : aList) {
    %>
    <tr>
        <td><%=admin.getId() %>
        </td>
        <td><%=admin.getUserName() %>
        </td>
        <td><%=admin.getPassword() %>
        </td>
        <td><a href=AdminModifg.jsp?aid=<%=admin.getId() %>>编辑用户</a>|<a
                href='javascript:DelAdmin(<%=admin.getId() %>)'>删除</a></td>
    </tr>
    <%} %>
    </tbody>
</table>
</body>
</html>
