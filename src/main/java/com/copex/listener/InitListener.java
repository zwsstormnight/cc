/*
 */
package com.copex.listener;

import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import com.copex.example.service.ComputerService;

/**
 * Listener - Initialize
 * 
 */
@Component("initListener")
public class InitListener implements ServletContextAware,
		ApplicationListener<ContextRefreshedEvent> {

	/** logger */
	private static final Logger logger = Logger.getLogger(InitListener.class
			.getName());

	/** servletContext */
	private ServletContext servletContext;

	@Value("${system.version}")
	private String systemVersion;

	@Resource(name = "computerServiceImpl")
	private ComputerService computerService;

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
		if (servletContext != null
				&& contextRefreshedEvent.getApplicationContext().getParent() == null) {
			String info = "I|n|i|t|i|a|l|i|z|i|n|g| |" + systemVersion;
			logger.info(info.replace("|", ""));

			computerService.buildAllStatic();
		}
	}

}