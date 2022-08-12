package com.alper.filter;

import javax.annotation.PostConstruct;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(filterName = "SessionFilter", urlPatterns = {"*.xhtml"})
public class SessionFilter implements Filter {

    private static final String AJAX_REDIRECT_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
            + "<partial-response><redirect url=\"%s\"></redirect></partial-response>";

    @PostConstruct
    public void init(){

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        try {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            HttpSession session = httpRequest.getSession(true);
            String path = httpRequest.getRequestURI();
            boolean loggedIn = (session != null) && (session.getAttribute("username") != null);
            boolean excludePage = path.contains("/login.xhtml");
            boolean excludePage2 = path.contains("/register.xhtml");
            boolean excludePage3 = path.contains("/easy");
            boolean resourceFile = httpRequest.getRequestURI().matches(
                    request.getServletContext().getContextPath() + "/javax.faces.resource/.*\\.xhtml.*");
            if (excludePage || excludePage2 || excludePage3 || resourceFile || loggedIn) {
                chain.doFilter(request, response);
            } else {
                boolean isAjaxRequest = "partial/ajax".equals(httpRequest.getHeader("Faces-Request"));
                if (isAjaxRequest) {
                    httpResponse.setContentType("text/xml");
                    httpResponse.setCharacterEncoding("UTF-8");
                    httpResponse.getWriter().printf(AJAX_REDIRECT_XML, "/login.xhtml");
                } else {
                    httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.xhtml");
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}