package com.copex.common.plugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import com.copex.common.FileInfo;
import com.copex.common.service.SettingService;

/**
 * Plugin - Local file Storage
 * 
 */
@Component("filePlugin")
public class FilePlugin extends StoragePlugin implements ServletContextAware {

	/** servletContext */
	private ServletContext servletContext;

	@Resource(name = "settingServiceImpl")
	private SettingService settingService;

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	@Override
	public String getName() {
		return "Local Storage";
	}

	@Override
	public void upload(String path, File file, String contentType) {
		File destFile = new File(servletContext.getRealPath(path));
		try {
			FileUtils.moveFile(file, destFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getUrl(String path) {
		//String siteUrl = settingService.get(SettingService.SITE_URL);
		return path;
	}

	@Override
	public List<FileInfo> browser(String path) {

		String siteUrl = settingService.get(SettingService.SITE_URL);

		List<FileInfo> fileInfos = new ArrayList<FileInfo>();
		File directory = new File(servletContext.getRealPath(path));
		if (directory.exists() && directory.isDirectory()) {
			for (File file : directory.listFiles()) {
				FileInfo fileInfo = new FileInfo();
				fileInfo.setName(file.getName());
				fileInfo.setUrl(siteUrl + path + file.getName());
				fileInfo.setIsDirectory(file.isDirectory());
				fileInfo.setSize(file.length());
				fileInfo.setLastModified(new Date(file.lastModified()));
				fileInfos.add(fileInfo);
			}
		}
		return fileInfos;
	}

}