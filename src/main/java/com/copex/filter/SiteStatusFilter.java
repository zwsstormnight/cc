/*
 */
package com.copex.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Filter - Site Status
 * 
 */
@Component("siteStatusFilter")
public class SiteStatusFilter extends OncePerRequestFilter {

	/** Default ignore URL */
	private static final String[] DEFAULT_IGNORE_URL_PATTERNS = new String[] { "/admin/**" };

	/** Default redirect URL */
	private static final String DEFAULT_REDIRECT_URL = "/common/site_close.jhtml";

	/** antPathMatcher */
	private static AntPathMatcher antPathMatcher = new AntPathMatcher();

	/** Ignore URL */
	private String[] ignoreUrlPatterns = DEFAULT_IGNORE_URL_PATTERNS;

	/** Redirect URL */
	private String redirectUrl = DEFAULT_REDIRECT_URL;

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO
		boolean isSiteEnabled = true;
		if (isSiteEnabled) {
			filterChain.doFilter(request, response);
		} else {
			String path = request.getServletPath();
			if (path.equals(redirectUrl)) {
				filterChain.doFilter(request, response);
				return;
			}
			if (ignoreUrlPatterns != null) {
				for (String ignoreUrlPattern : ignoreUrlPatterns) {
					if (antPathMatcher.match(ignoreUrlPattern, path)) {
						filterChain.doFilter(request, response);
						return;
					}
				}
			}
			response.sendRedirect(request.getContextPath() + redirectUrl);
		}
	}

	public String[] getIgnoreUrlPatterns() {
		return ignoreUrlPatterns;
	}

	public void setIgnoreUrlPatterns(String[] ignoreUrlPatterns) {
		this.ignoreUrlPatterns = ignoreUrlPatterns;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

}