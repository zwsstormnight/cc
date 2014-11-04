package com.copex.common.service.impl;

import org.springframework.stereotype.Service;

import com.copex.common.service.SettingService;

@Service("settingServiceImpl")
public class SettingServiceMock implements SettingService {

	@Override
	public String get(String key) {
		if(key.equals(FILE_UPLOAD_PATH)) {
			return "/upload/file/${.now?string('yyyyMM')}/";
		} else if (key.equals(IMAGE_UPLOAD_PATH)) {
			return "/upload/image/${.now?string('yyyyMM')}/"; 
		} else if (key.equals(MEDIA_UPLAOD_PATH)) {
			return "/upload/media/${.now?string('yyyyMM')}/";
		} 
		return null;
	}

	@Override
	public void set(String key, String value) {
		// TODO Auto-generated method stub
	}

}
