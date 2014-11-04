package com.copex.common.service;

public interface SettingService {

	public static String UPLOAD_MAX_SIZE = "uploadMaxSize";

	public static String MEDIA_UPLAOD_PATH = "mediaUploadPath";

	public static String FILE_UPLOAD_PATH = "fileUploadPath";

	public static String IMAGE_UPLOAD_PATH = "imageUploadPath";
	
	public static String SITE_URL = "siteURL";

	public String get(String key);

	public void set(String key, String value);
}
