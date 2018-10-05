package com.stpl.servicemix.example.osgiservlet.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        writer.println("<html><body><pre>");
        writer.println("Servlet path =" + req.getServletPath());
        writer.println("Path info =" + req.getPathInfo());
        writer.println("Query string =" + req.getQueryString());
        writer.println("</pre></body></html>");
    }
}
