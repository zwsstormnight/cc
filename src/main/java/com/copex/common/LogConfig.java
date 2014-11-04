/*
 */
package com.copex.common;

import java.io.Serializable;

/**
 * Log Configuration
 * 
 */
public class LogConfig implements Serializable {

	private static final long serialVersionUID = -3595672732904893923L;

	private String operation;
	private String urlPattern;

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getUrlPattern() {
		return urlPattern;
	}

	public void setUrlPattern(String urlPattern) {
		this.urlPattern = urlPattern;
	}

}