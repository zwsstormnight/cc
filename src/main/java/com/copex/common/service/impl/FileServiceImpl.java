package com.copex.common.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

import com.copex.common.FileInfo;
import com.copex.common.FileInfo.FileType;
import com.copex.common.FileInfo.OrderType;
import com.copex.common.plugin.StoragePlugin;
import com.copex.common.service.FileService;
import com.copex.common.service.SettingService;
import com.copex.core.util.FreemarkerUtils;

/**
 * Service - File
 */
@Service("fileServiceImpl")
public class FileServiceImpl implements FileService, ServletContextAware {

	/** servletContext */
	private ServletContext servletContext;

	@Resource(name = "taskExecutor")
	private TaskExecutor taskExecutor;

	@Resource(name = "filePlugin")
	private StoragePlugin storagePlugin;

	@Resource(name = "settingServiceImpl")
	private SettingService settingService;

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	/**
	 * add upload task
	 * 
	 * @param storagePlugin
	 * @param path
	 * @param tempFile
	 * @param contentType
	 */
	private void addTask(final StoragePlugin storagePlugin, final String path,
			final File tempFile, final String contentType) {
		taskExecutor.execute(new Runnable() {
			public void run() {
				try {
					storagePlugin.upload(path, tempFile, contentType);
				} finally {
					FileUtils.deleteQuietly(tempFile);
				}
			}
		});
	}

	public String upload(FileType fileType, MultipartFile multipartFile,
			boolean async) {
		if (multipartFile == null) {
			return null;
		}

		String uploadPath = getUploadPath(fileType);
		try {
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("uuid", UUID.randomUUID().toString());
			String path = FreemarkerUtils.process(uploadPath, model);
			String destPath = path
					+ UUID.randomUUID()
					+ "."
					+ FilenameUtils.getExtension(multipartFile
							.getOriginalFilename());

			File tempFile = new File(System.getProperty("java.io.tmpdir")
					+ "/upload_" + UUID.randomUUID() + ".tmp");
			if (!tempFile.getParentFile().exists()) {
				tempFile.getParentFile().mkdirs();
			}
			multipartFile.transferTo(tempFile);
			if (async) {
				addTask(storagePlugin, destPath, tempFile,
						multipartFile.getContentType());
			} else {
				try {
					storagePlugin.upload(destPath, tempFile,
							multipartFile.getContentType());
				} finally {
					FileUtils.deleteQuietly(tempFile);
				}
			}
			return storagePlugin.getUrl(destPath);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String upload(FileType fileType, MultipartFile multipartFile) {
		return upload(fileType, multipartFile, false);
	}

	public String uploadLocal(FileType fileType, MultipartFile multipartFile) {
		if (multipartFile == null) {
			return null;
		}
		String uploadPath = getUploadPath(fileType);
		try {
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("uuid", UUID.randomUUID().toString());
			String path = FreemarkerUtils.process(uploadPath, model);
			String destPath = path
					+ UUID.randomUUID()
					+ "."
					+ FilenameUtils.getExtension(multipartFile
							.getOriginalFilename());
			File destFile = new File(servletContext.getRealPath(destPath));
			if (!destFile.getParentFile().exists()) {
				destFile.getParentFile().mkdirs();
			}
			multipartFile.transferTo(destFile);
			return destPath;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<FileInfo> browser(String path, FileType fileType,
			OrderType orderType) {
		if (path != null) {
			if (!path.startsWith("/")) {
				path = "/" + path;
			}
			if (!path.endsWith("/")) {
				path += "/";
			}
		} else {
			path = "/";
		}
		String uploadPath = getUploadPath(fileType);
		String browsePath = StringUtils.substringBefore(uploadPath, "${");
		browsePath = StringUtils.substringBeforeLast(browsePath, "/") + path;

		List<FileInfo> fileInfos = new ArrayList<FileInfo>();
		if (browsePath.indexOf("..") >= 0) {
			return fileInfos;
		}

		fileInfos = storagePlugin.browser(browsePath);

		if (orderType == OrderType.size) {
			Collections.sort(fileInfos, new SizeComparator());
		} else if (orderType == OrderType.type) {
			Collections.sort(fileInfos, new TypeComparator());
		} else {
			Collections.sort(fileInfos, new NameComparator());
		}
		return fileInfos;
	}

	private String getUploadPath(FileType fileType) {
		String uploadPath;
		if (fileType == FileType.media) {
			uploadPath = settingService.get(SettingService.MEDIA_UPLAOD_PATH);
		} else if (fileType == FileType.file) {
			uploadPath = settingService.get(SettingService.FILE_UPLOAD_PATH);
		} else {
			uploadPath = settingService.get(SettingService.IMAGE_UPLOAD_PATH);
		}
		return uploadPath;
	}

	private class NameComparator implements Comparator<FileInfo> {
		public int compare(FileInfo fileInfos1, FileInfo fileInfos2) {
			return new CompareToBuilder()
					.append(!fileInfos1.getIsDirectory(),
							!fileInfos2.getIsDirectory())
					.append(fileInfos1.getName(), fileInfos2.getName())
					.toComparison();
		}
	}

	private class SizeComparator implements Comparator<FileInfo> {
		public int compare(FileInfo fileInfos1, FileInfo fileInfos2) {
			return new CompareToBuilder()
					.append(!fileInfos1.getIsDirectory(),
							!fileInfos2.getIsDirectory())
					.append(fileInfos1.getSize(), fileInfos2.getSize())
					.toComparison();
		}
	}

	private class TypeComparator implements Comparator<FileInfo> {
		public int compare(FileInfo fileInfos1, FileInfo fileInfos2) {
			return new CompareToBuilder()
					.append(!fileInfos1.getIsDirectory(),
							!fileInfos2.getIsDirectory())
					.append(FilenameUtils.getExtension(fileInfos1.getName()),
							FilenameUtils.getExtension(fileInfos2.getName()))
					.toComparison();
		}
	}

}