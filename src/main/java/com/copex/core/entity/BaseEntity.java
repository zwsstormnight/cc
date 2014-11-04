/*
 *
 */
package com.copex.core.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.groups.Default;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.copex.core.listener.EntityListener;

/**
 * Entity base class
 * 
 */
@JsonAutoDetect(fieldVisibility = Visibility.NONE, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, creatorVisibility = Visibility.NONE)
@EntityListeners(EntityListener.class)
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

	private static final long serialVersionUID = -67188388306700736L;

	public static final String ID_PROPERTY_NAME = "id";

	public static final String CREATE_DATE_PROPERTY_NAME = "createDate";

	public static final String MODIFY_DATE_PROPERTY_NAME = "modifyDate";

	/**
	 * Save Validation group
	 */
	public interface Save extends Default {

	}

	/**
	 * Update Validation group
	 */
	public interface Update extends Default {

	}

	/** ID */
	@JsonProperty
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/** Create Date */
	@JsonProperty
	@Column(name = "create_date", nullable = false, updatable = false)
	private Date createDate;

	/** Modify Date */
	@JsonProperty
	@Column(name = "modify_date", nullable = false)
	private Date modifyDate;

	/**
	 * Get ID
	 * 
	 * @return ID
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Set ID
	 * 
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Get create date
	 * 
	 * @return 
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * Set create date
	 * 
	 * @param createDate
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * Get modify date
	 * 
	 * @return
	 */
	public Date getModifyDate() {
		return modifyDate;
	}

	/**
	 * Set modify date
	 * 
	 * @param modifyDate
	 */
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		if (!BaseEntity.class.isAssignableFrom(obj.getClass())) {
			return false;
		}
		BaseEntity other = (BaseEntity) obj;
		return getId() != null ? getId().equals(other.getId()) : false;
	}

	@Override
	public int hashCode() {
		int hashCode = 17;
		hashCode += null == getId() ? 0 : getId().hashCode() * 31;
		return hashCode;
	}

}