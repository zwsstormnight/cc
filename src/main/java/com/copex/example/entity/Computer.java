package com.copex.example.entity;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.dom4j.io.SAXReader;
import org.springframework.core.io.ClassPathResource;

import com.copex.common.CommonAttributes;
import com.copex.core.Staticizable;
import com.copex.core.entity.BaseEntity;
import com.copex.core.util.FreemarkerUtils;

import freemarker.template.TemplateException;

@Entity
@Table(name = "computer")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "computer_sequence")
public class Computer extends BaseEntity implements Staticizable {

	private static final long serialVersionUID = 8636771994958480630L;

	private static String staticPath;

	@NotNull
	@Size(min = 5, message = "{example.min.5}")
	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "introduced")
	@Temporal(TemporalType.DATE)
	private Date introduced;

	@Column(name = "discontinued")
	@Temporal(TemporalType.DATE)
	private Date discontinued;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "company_id")
	private Company company;
	
	@Column(name ="image")
	private String image;

	static {
		try {
			InputStream ins = new ClassPathResource(
					CommonAttributes.TEMPLATE_XML_PATH).getInputStream();
			org.dom4j.Document document = new SAXReader().read(ins);
			org.dom4j.Element element = (org.dom4j.Element) document
					.selectSingleNode("/templates/template[@id='computerContent']");
			staticPath = element.attributeValue("staticPath");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getIntroduced() {
		return introduced;
	}

	public void setIntroduced(Date introduced) {
		this.introduced = introduced;
	}

	public Date getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(Date discontinued) {
		this.discontinued = discontinued;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	@Transient
	public String getPath() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("id", getId());
		model.put("createDate", getCreateDate());
		model.put("modifyDate", getModifyDate());
		try {
			return FreemarkerUtils.process(staticPath, model);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@Transient
	public String getStaticId() {
		return "computerContent";
	}

	@Override
	@Transient
	public String getObjectKey() {
		return "computer";
	}

}
