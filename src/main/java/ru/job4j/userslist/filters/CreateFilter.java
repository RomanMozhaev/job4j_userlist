package ru.job4j.userslist.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * the filter for creating and deleting.
 * Only admins could crate and delete users.
 */
public class CreateFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        synchronized (session) {
            if (!session.getAttribute("role").equals("admin")) {
                ((HttpServletResponse) servletResponse).sendRedirect(String.format("%s/view", request.getContextPath()));
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        }
    }

    @Override
    public void destroy() {

    }
}