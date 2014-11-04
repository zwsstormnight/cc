/*
 */
package com.copex.core;

import java.io.Serializable;

/**
 * Template
 * 
 */
public class Template implements Serializable {

	private static final long serialVersionUID = -517540800437045215L;

	/**
	 * Type
	 */
	public enum Type {
		page, mail, print
	}

	private String id;

	private Type type;

	private String name;

	private String templatePath;

	private String staticPath;

	private String description;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTemplatePath() {
		return templatePath;
	}

	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}

	public String getStaticPath() {
		return staticPath;
	}

	public void setStaticPath(String staticPath) {
		this.staticPath = staticPath;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}