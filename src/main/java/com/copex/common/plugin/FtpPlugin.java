package com.copex.common.plugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.copex.common.FileInfo;

/**
 * Plugin - FTP
 * 
 */
@Component("ftpPlugin")
public class FtpPlugin extends StoragePlugin {

	@Value("${ftp.host}")
	private String host;

	@Value("${ftp.port}")
	private String port;

	@Value("${ftp.username}")
	private String username;

	@Value("${ftp.pasword}")
	private String password;

	@Value("$")
	private String urlPrefix;

	@Override
	public String getName() {
		return "FTP Storage";
	}

	@Override
	public void upload(String path, File file, String contentType) {

		FTPClient ftpClient = new FTPClient();
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(file);
			ftpClient.connect(host, Integer.valueOf(port));
			ftpClient.login(username, password);
			ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			ftpClient.enterLocalPassiveMode();
			if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
				String directory = StringUtils.substringBeforeLast(path, "/");
				String filename = StringUtils.substringAfterLast(path, "/");
				if (!ftpClient.changeWorkingDirectory(directory)) {
					String[] paths = StringUtils.split(directory, "/");
					String p = "/";
					ftpClient.changeWorkingDirectory(p);
					for (String s : paths) {
						p += s + "/";
						if (!ftpClient.changeWorkingDirectory(p)) {
							ftpClient.makeDirectory(s);
							ftpClient.changeWorkingDirectory(p);
						}
					}
				}
				ftpClient.storeFile(filename, inputStream);
				ftpClient.logout();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(inputStream);
			if (ftpClient.isConnected()) {
				try {
					ftpClient.disconnect();
				} catch (IOException e) {
				}
			}
		}

	}

	@Override
	public String getUrl(String path) {
		return urlPrefix + path;

	}

	@Override
	public List<FileInfo> browser(String path) {
		List<FileInfo> fileInfos = new ArrayList<FileInfo>();

		FTPClient ftpClient = new FTPClient();
		try {
			ftpClient.connect(host, Integer.valueOf(port));
			ftpClient.login(username, password);
			ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			ftpClient.enterLocalPassiveMode();
			if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())
					&& ftpClient.changeWorkingDirectory(path)) {
				for (FTPFile ftpFile : ftpClient.listFiles()) {
					FileInfo fileInfo = new FileInfo();
					fileInfo.setName(ftpFile.getName());
					fileInfo.setUrl(urlPrefix + path + ftpFile.getName());
					fileInfo.setIsDirectory(ftpFile.isDirectory());
					fileInfo.setSize(ftpFile.getSize());
					fileInfo.setLastModified(ftpFile.getTimestamp().getTime());
					fileInfos.add(fileInfo);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ftpClient.isConnected()) {
				try {
					ftpClient.disconnect();
				} catch (IOException e) {
				}
			}
		}

		return fileInfos;
	}

}