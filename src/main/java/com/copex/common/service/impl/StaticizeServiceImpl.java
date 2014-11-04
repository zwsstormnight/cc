package com.copex.common.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.copex.common.service.StaticizeService;
import com.copex.common.service.TemplateService;
import com.copex.core.Staticizable;
import com.copex.core.Template;

@Service("staticizeServiceImpl")
public class StaticizeServiceImpl implements StaticizeService,
		ServletContextAware {

	@Resource(name = "freeMarkerConfigurer")
	private FreeMarkerConfigurer freeMarkerConfigurer;

	@Resource(name = "templateServiceImpl")
	private TemplateService templateService;

	/** servletContext */
	private ServletContext servletContext;

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	@Override
	@Transactional(readOnly = true)
	public int build(Staticizable entity) {
		Assert.notNull(entity);

		delete(entity);
		Template template = getTemplate(entity.getStaticId());
		int buildCount = 0;
		Map<String, Object> model = new HashMap<String, Object>();
		model.put(entity.getObjectKey(), entity);
		buildCount += build(template.getTemplatePath(), entity.getPath(), model);

		return buildCount;
	}

	@Override
	@Transactional(readOnly = true)
	public int delete(Staticizable entity) {
		Assert.notNull(entity);

		return delete(entity.getPath());
	}

	@Override
	@Transactional(readOnly = true)
	public int build(String templatePath, String staticPath,
			Map<String, Object> model) {
		Assert.hasText(templatePath);
		Assert.hasText(staticPath);

		FileOutputStream fileOutputStream = null;
		OutputStreamWriter outputStreamWriter = null;
		Writer writer = null;
		try {
			freemarker.template.Template template = freeMarkerConfigurer
					.getConfiguration().getTemplate(templatePath);
			File staticFile = new File(servletContext.getRealPath(staticPath));
			File staticDirectory = staticFile.getParentFile();
			if (!staticDirectory.exists()) {
				staticDirectory.mkdirs();
			}
			fileOutputStream = new FileOutputStream(staticFile);
			outputStreamWriter = new OutputStreamWriter(fileOutputStream,
					"UTF-8");
			writer = new BufferedWriter(outputStreamWriter);
			template.process(model, writer);
			writer.flush();
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(writer);
			IOUtils.closeQuietly(outputStreamWriter);
			IOUtils.closeQuietly(fileOutputStream);
		}
		return 0;
	}

	@Override
	@Transactional(readOnly = true)
	public int build(String templatePath, String staticPath) {
		return build(templatePath, staticPath, null);
	}

	@Override
	@Transactional(readOnly = true)
	public int delete(String staticPath) {
		Assert.hasText(staticPath);

		File staticFile = new File(servletContext.getRealPath(staticPath));
		if (staticFile.exists()) {
			staticFile.delete();
			return 1;
		}
		return 0;
	}

	@Override
	@Transactional(readOnly = true)
	public int buildIndex() {
		Template template = getTemplate("index");
		return build(template.getTemplatePath(), template.getStaticPath());
	}

	@Override
	@Transactional(readOnly = true)
	public int buildOther() {
		// TODO
		return 0;
	}

	@Override
	public int buildAll() {
		// TODO
		return 0;
	}

	@Override
	public int deleteIndex() {
		Template template = getTemplate("index");
		return delete(template.getStaticPath());
	}

	@Override
	public int deleteOther() {
		// TODO
		return 0;
	}

	private Template getTemplate(String id) {
		return templateService.get(id);
	}
}
