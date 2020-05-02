package ru.job4j.userslist.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * the filter checks if the user is signed in or not.
 */
public class AuthorizationFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        if (request.getRequestURI().contains("/signin")) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            HttpSession session = request.getSession();
            synchronized (session) {
                if (session.getAttribute("id") == null) {
                    ((HttpServletResponse) servletResponse).sendRedirect(String.format("%s/signin", request.getContextPath()));
                } else  {
                    filterChain.doFilter(servletRequest, servletResponse);
                }
            }
        }
    }

    @Override
    public void destroy() {

    }
}