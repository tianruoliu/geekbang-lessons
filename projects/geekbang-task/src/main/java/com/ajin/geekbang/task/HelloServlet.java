package com.ajin.geekbang.task;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ajin
 */

public class HelloServlet extends HttpServlet {

    public static final ThreadLocal<HttpServletResponse> responseHolder = new ThreadLocal<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            responseHolder.set(resp);
            req.getRequestDispatcher("/WEB-INF/jsp/customize.jsp").forward(req, resp);
        } finally {
            responseHolder.remove();
        }
    }
}
