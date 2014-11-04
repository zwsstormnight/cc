/*
 */
package com.copex.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * Filter - Deny Access
 * 
 */
public class AccessDeniedFilter implements Filter {

	private static final String ERROR_MESSAGE = "Access denied!";

	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		response.addHeader("PoweredBy", "XXXX");
		response.sendError(HttpServletResponse.SC_FORBIDDEN, ERROR_MESSAGE);
	}

}