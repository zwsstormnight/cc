/*
 */
package com.copex.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import com.copex.*;
import com.copex.core.entity.BaseEntity;

/**
 * Entity - Log
 * 
 */
@Entity
@Table(name = "log")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "log_sequence")
public class Log extends BaseEntity {

	private static final long serialVersionUID = 6864646078197573968L;

	public static final String LOG_CONTENT_ATTRIBUTE_NAME = Log.class.getName()
			+ ".CONTENT";

	private String operation;

	private String operator;

	private String content;

	private String parameter;

	private String ip;

	@Column(nullable = false, updatable = false)
	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	@Column(updatable = false)
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	@Column(length = 3000, updatable = false)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Lob
	@Column(updatable = false)
	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	@Column(nullable = false, updatable = false)
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

}