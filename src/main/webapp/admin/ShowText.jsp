<%@ page language="java" import="com.questionnaire.survey.dao.*" pageEncoding="UTF-8" %>
<%@ page import="com.questionnaire.survey.entity.*" %>
<%@ page import="java.util.List" %>
<%
    String qid = request.getParameter("qid");
    QuestionDAO qdao = DAOFactory.getQuestionDAO();
    Question q = qdao.findQuestion(Long.valueOf(qid));
    TextDAO tdao = DAOFactory.getTextDAO();
    List<Text> tlist = tdao.listAllText(Long.valueOf(qid));
%>


<script language="JavaScript" src="../Js/Func.js"></script>
<script language="javascript1.2">window.onload = tableFix;</script>


<link rel="stylesheet" href="../Css/Admin.css" type="text/css"/>
<div id="SuveyAdmin">
    <table width="100%" align="center" cellpadding="0" cellspacing="0" id="SurveyAdmin" class="table">

        <tr>
            <th><%=q.getQHead() %>
            </th>
        </tr>

        <tr>
            <td>
                <ul type=1>
                    <%
                        for (Text t : tlist) {
                    %>
                    <li><%=t.getTContent() %>
                    </li>
                    <%
                        }
                    %>

                </ul>
            </td>
        </tr>


    </table>
</div>

