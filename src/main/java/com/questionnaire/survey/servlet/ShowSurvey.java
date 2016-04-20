package com.questionnaire.survey.servlet;

import com.questionnaire.survey.dao.*;
import com.questionnaire.survey.entity.Answersheet;
import com.questionnaire.survey.entity.Question;
import com.questionnaire.survey.entity.Survey;
import com.questionnaire.survey.entity.Text;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Set;
import java.util.TreeSet;

public class ShowSurvey extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 5900463445573456509L;

    /**
     * The doGet method of the servlet. <br>
     * <p/>
     * This method is called when a form has its tag value method equals to get.
     *
     * @param request  the request send by the client to the server
     * @param response the response send by the server to the client
     * @throws ServletException if an error occurred
     * @throws IOException      if an error occurred
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * The doPost method of the servlet. <br>
     * <p/>
     * This method is called when a form has its tag value method equals to post.
     *
     * @param request  the request send by the client to the server
     * @param response the response send by the server to the client
     * @throws ServletException if an error occurred
     * @throws IOException      if an error occurred
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String sid = request.getParameter("sid");
        SurveyDAO sdao1 = DAOFactory.getSurveyDAO();
        Survey s = sdao1.findSurvey(Long.valueOf(sid));
        AnswersheetDAO adao1 = DAOFactory.getAnswersheetDAO();
        boolean ipRepeatSetting = s.getSIpRepeat();
        boolean isIpRepeat = adao1.isIpRepeat(Long.valueOf(sid), request.getRemoteAddr());
        if (!ipRepeatSetting && isIpRepeat) {
            response.sendRedirect("../OpResult.jsp?op=default&ret=false&words=" + URLEncoder.encode("操作失败，该问卷设置了禁止同一IP重复提交，您的IP已经过提交问卷！", "UTF-8"));
            return;
        }
        QuestionDAO dao = DAOFactory.getQuestionDAO();
        TextDAO tdao = DAOFactory.getTextDAO();
        Enumeration params = request.getParameterNames();
        Set<Long> qidSet = new TreeSet<Long>();
        while (params.hasMoreElements()) {
            String para = (String) params.nextElement();
            if (para.startsWith("answer")) {
                Long qid = Long.valueOf(para.substring(6));
                qidSet.add(qid);
                //System.out.println("qid:"+qid);
            }
        }

        boolean success = false;
        String answersheet = "";
        synchronized (this) {
            for (Long qid : qidSet) {
                Question q = dao.findQuestion(qid);
                String result = q.getQResult();
                String[] results = result.split(",");
                //System.out.println(q.getQType());
                //for(String s:results)
                //System.out.println("results is:"+s);

                //results[1]=  String.valueOf(Integer.parseInt(results[1])+1)  ;
                if (q.getQType() == 1) {
                    int answer = Integer.parseInt(request.getParameter("answer" + qid));
                    results[answer] = String.valueOf(Integer.parseInt(results[answer]) + 1);
                    answersheet = answersheet + "&@@&" + qid + ":as=" + answer + ";";
                    //System.out.println("answersheet1:"+answersheet);
                } else if (q.getQType() == 2) {
                    String[] answers = request.getParameterValues("answer" + qid);
                    String as = "";
                    for (int i = 0; i < answers.length; i++) {
                        results[Integer.parseInt(answers[i])] = String.valueOf(Integer.parseInt(results[Integer.parseInt(answers[i])]) + 1);
                        as = as + "," + answers[i];
                    }
                    as = as.substring(1, as.length());
                    answersheet = answersheet + "&@@&" + qid + ":as=" + as + ";";
                    //System.out.println("as2:"+as);
                    //System.out.println("answersheet2:"+answersheet);

                } else if (q.getQType() == 3) {
                    int answer = Integer.parseInt(request.getParameter("answer" + qid));
                    results[answer] = String.valueOf(Integer.parseInt(results[answer]) + 1);

                    String txtanswer = "";
                    if (answer == results.length - 1) {
                        txtanswer = request.getParameter("txtanswer" + qid);
                        Text text = new Text();
                        text.setQId(qid);
                        text.setTContent(txtanswer);
                        tdao.addText(text);
                    }
                    answersheet = answersheet + "&@@&" + qid + ":as=" + answer + ";" + (txtanswer == "" ? "" : "text=" + txtanswer);
                    //System.out.println("answersheet3:"+answersheet);

                } else if (q.getQType() == 4) {
                    String[] answers = request.getParameterValues("answer" + qid);
                    String as = "";
                    for (String answer : answers) {
                        results[Integer.parseInt(answer)] = String.valueOf(Integer.parseInt(results[Integer.parseInt(answer)]) + 1);
                        as = as + "," + answer;
                    }
                    String txtanswer = null;
                    //System.out.println("answers.length:"+answers.length+"results.length:"+results.length);
                    if (Integer.valueOf(answers[answers.length - 1]) == results.length - 1) {
                        txtanswer = request.getParameter("txtanswer" + qid);
                        Text text = new Text();
                        text.setQId(qid);
                        text.setTContent(txtanswer);
                        tdao.addText(text);
                    }
                    as = as.substring(1, as.length());
                    answersheet = answersheet + "&@@&" + qid + ":as=" + as + ";" + (txtanswer == null ? "" : "text=" + txtanswer);
                    //System.out.println("as4:"+as);
                    //System.out.println("answersheet4:"+answersheet);

                } else if (q.getQType() == 5) {
                    String txtanswer = request.getParameter("answer" + qid);
                    Text text = new Text();
                    text.setQId(qid);
                    text.setTContent(txtanswer);
                    tdao.addText(text);
                    answersheet = answersheet + "&@@&" + qid + ":text=" + txtanswer;
                    //System.out.println("answersheet5:"+answersheet);
                }

                String newresult = "";
                for (int i = 0; i < results.length; i++) {
                    if (i == 0) {
                        newresult = results[i];
                    } else {
                        newresult = newresult + "," + results[i];
                    }
                }
                q.setQResult(newresult);
                //System.out.print(result);
                if (dao.updateQuestion(q)) {
                    success = true;
                }
            }
            AnswersheetDAO adao = DAOFactory.getAnswersheetDAO();
            Answersheet sheet = new Answersheet();
            sheet.setSurvey(Long.valueOf(sid));

            //~~~
            //System.out.println(answersheet);
            answersheet = answersheet.substring(4);
            sheet.setAsResult(answersheet);
            sheet.setAsPostdate(new java.util.Date());
            sheet.setAsUserIp(request.getRemoteAddr());
            if (adao.addAnswersheet(sheet)) {
                success = true;
            }
            if (success) {
                SurveyDAO sdao = DAOFactory.getSurveyDAO();
                Survey survey = sdao.findSurvey(Long.valueOf(sid));
                synchronized (this) {
                    survey.setSUsehits(survey.getSUsehits() + 1);
                    sdao.updateSurvey(survey);
                }
                response.sendRedirect("../OpResult.jsp?ret=true&op=ShowSurvey");
            } else
                response.sendRedirect("../OpResult.jsp?ret=false&op=ShowSurvey");
        }
    }

}
