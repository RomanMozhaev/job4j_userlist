package ru.job4j.userslist.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * the filter for updating.
 * the non-admins could update only their own account.
 */
public class UpdateFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        synchronized (session) {
            int id = (Integer) session.getAttribute("id");
            if (!session.getAttribute("role").equals("admin")) {
                if (request.getQueryString().contains("id=" + id)) {
                    filterChain.doFilter(servletRequest, servletResponse);
                } else {
                    ((HttpServletResponse) servletResponse).sendRedirect(String.format("%s/view", request.getContextPath()));
                }
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        }
    }

    @Override
    public void destroy() {

    }
}