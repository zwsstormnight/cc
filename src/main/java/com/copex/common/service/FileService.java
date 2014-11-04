package com.copex.common.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.copex.common.FileInfo;
import com.copex.common.FileInfo.FileType;
import com.copex.common.FileInfo.OrderType;

/**
 * Service - File
 * 
 */
public interface FileService {

	/**
	 * Upload file
	 * 
	 * @param fileType
	 * @param multipartFile
	 * @param async
	 * @return file URL
	 */
	String upload(FileType fileType, MultipartFile multipartFile, boolean async);

	/**
	 * Upload file asynchronously
	 * 
	 * @param fileType
	 * @param multipartFile
	 * @return file URL
	 */
	String upload(FileType fileType, MultipartFile multipartFile);

	/**
	 * Upload file to local
	 * 
	 * @param fileType
	 * @param multipartFile
	 * @return file path
	 */
	String uploadLocal(FileType fileType, MultipartFile multipartFile);

	/**
	 * Browser file
	 * 
	 * @param path
	 * @param fileType
	 * @param orderType
	 * @return list of file information
	 */
	List<FileInfo> browser(String path, FileType fileType, OrderType orderType);

}