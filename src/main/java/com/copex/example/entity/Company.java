package com.copex.example.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.copex.core.entity.BaseEntity;

@Entity
@Table(name = "company")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "company_sequence")
public class Company extends BaseEntity {

	private static final long serialVersionUID = 5825127650630436648L;

	@NotNull
	@Column(name = "name", nullable = false)
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
