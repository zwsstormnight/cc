package com.copex.common;

import java.util.Date;

/**
 * File information
 *
 */
public class FileInfo {

	/**
	 * File type
	 */
	public enum FileType {
		image, media, file
	}

	/**
	 * Order type
	 */
	public enum OrderType {
		name, size, type
	}

	private String name;

	private String url;

	private Boolean isDirectory;

	private Long size;

	private Date lastModified;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Boolean getIsDirectory() {
		return isDirectory;
	}

	public void setIsDirectory(Boolean isDirectory) {
		this.isDirectory = isDirectory;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

}