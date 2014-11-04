/*
 */
package com.copex.common.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;

import com.copex.common.CommonAttributes;
import com.copex.common.service.TemplateService;
import com.copex.core.Template;
import com.copex.core.Template.Type;

/**
 * Service - Template
 * 
 */
@Service("templateServiceImpl")
public class TemplateServiceImpl implements TemplateService,
		ServletContextAware {

	/** servletContext */
	private ServletContext servletContext;

	@Value("${template.loader_path}")
	private String[] templateLoaderPaths;

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	@Cacheable("template")
	public List<Template> getAll() {
		return getList("/templates/template");
	}

	@Cacheable("template")
	public List<Template> getList(Type type) {
		if (type != null) {
			return getList("/templates/template[@type='" + type + "']");
		} else {
			return getAll();
		}
	}

	@SuppressWarnings("unchecked")
	private List<Template> getList(String path) {
		try {
			File templateXmlFile = new ClassPathResource(
					CommonAttributes.TEMPLATE_XML_PATH).getFile();
			Document document = new SAXReader().read(templateXmlFile);
			List<Template> templates = new ArrayList<Template>();
			List<Element> elements = document.selectNodes(path);
			for (Element element : elements) {
				Template template = getTemplate(element);
				templates.add(template);
			}
			return templates;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Cacheable("template")
	public Template get(String id) {
		try {
			File settingXmlFile = new ClassPathResource(
					CommonAttributes.TEMPLATE_XML_PATH).getFile();
			Document document = new SAXReader().read(settingXmlFile);
			Element element = (Element) document
					.selectSingleNode("/templates/template[@id='" + id + "']");
			Template template = getTemplate(element);
			return template;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String read(String id) {
		Template template = get(id);
		return read(template);
	}

	public String read(Template template) {
		String templatePath = servletContext.getRealPath(templateLoaderPaths[0]
				+ template.getTemplatePath());
		File templateFile = new File(templatePath);
		String templateContent = null;
		try {
			templateContent = FileUtils.readFileToString(templateFile, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return templateContent;
	}

	public void write(String id, String content) {
		Template template = get(id);
		write(template, content);
	}

	public void write(Template template, String content) {
		String templatePath = servletContext.getRealPath(templateLoaderPaths[0]
				+ template.getTemplatePath());
		File templateFile = new File(templatePath);
		try {
			FileUtils.writeStringToFile(templateFile, content, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Template getTemplate(Element element) {
		String id = element.attributeValue("id");
		String type = element.attributeValue("type");
		String name = element.attributeValue("name");
		String templatePath = element.attributeValue("templatePath");
		String staticPath = element.attributeValue("staticPath");
		String description = element.attributeValue("description");

		Template template = new Template();
		template.setId(id);
		template.setType(Type.valueOf(type));
		template.setName(name);
		template.setTemplatePath(templatePath);
		template.setStaticPath(staticPath);
		template.setDescription(description);
		return template;
	}

}