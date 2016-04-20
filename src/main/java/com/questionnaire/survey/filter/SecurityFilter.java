package com.questionnaire.survey.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class SecurityFilter implements Filter {

    public SecurityFilter() {
        // TODO Auto-generated constructor stub
    }

    public void destroy() {
        // TODO Auto-generated method stub

    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpSession session = ((HttpServletRequest) request).getSession();
        if (session.getAttribute("username") != null && (!"".equals(session.getAttribute("username"))))
            chain.doFilter(request, response);
        else
            ((HttpServletResponse) response).sendRedirect("../login.jsp");

    }

    public void init(FilterConfig config) throws ServletException {
        // TODO Auto-generated method stub

    }

}
