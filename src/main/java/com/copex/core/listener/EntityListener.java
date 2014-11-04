/*
 */
package com.copex.core.listener;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.copex.core.entity.BaseEntity;

/**
 * Listener - Create Date and Modify Date 
 * 
 */
public class EntityListener {

	/**
	 * 
	 * @param entity
	 */
	@PrePersist
	public void prePersist(BaseEntity entity) {
		entity.setCreateDate(new Date());
		entity.setModifyDate(new Date());
	}

	/**
	 * 
	 * @param entity
	 * 
	 */
	@PreUpdate
	public void preUpdate(BaseEntity entity) {
		entity.setModifyDate(new Date());
	}

}