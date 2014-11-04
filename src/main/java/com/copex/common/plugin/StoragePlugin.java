package com.copex.common.plugin;

import java.io.File;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.springframework.stereotype.Component;

import com.copex.common.FileInfo;

/**
 * Plugin - Storage
 * 
 */
public abstract class StoragePlugin {

	/**
	 * Get ID
	 * 
	 * @return ID
	 */
	public final String getId() {
		return getClass().getAnnotation(Component.class).value();
	}

	/**
	 * Get name
	 * 
	 * @return name
	 */
	public abstract String getName();

	/**
	 * Upload file
	 * 
	 * @param path
	 * @param file
	 * @param contentType
	 */
	public abstract void upload(String path, File file, String contentType);

	/**
	 * Get URL
	 * 
	 * @param path
	 * @return URL
	 */
	public abstract String getUrl(String path);

	/**
	 * Browser file
	 * 
	 * @param path
	 * @return list of file information
	 */
	public abstract List<FileInfo> browser(String path);

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		StoragePlugin other = (StoragePlugin) obj;
		return new EqualsBuilder().append(getId(), other.getId()).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(getId()).toHashCode();
	}

}