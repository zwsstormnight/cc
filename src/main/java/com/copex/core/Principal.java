/*
 */
package com.copex.core;

import java.io.Serializable;

/**
 * Principal
 * 
 */
public class Principal implements Serializable {

	private static final long serialVersionUID = 5798882004228239559L;

	/** ID */
	private Long id;

	/** User name */
	private String username;

	/**
	 * @param id
	 * @param username
	 */
	public Principal(Long id, String username) {
		this.id = id;
		this.username = username;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return username;
	}

}