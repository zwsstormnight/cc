/*
 */
package com.copex.common.service.impl;

import java.awt.image.BufferedImage;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.copex.common.service.CaptchaService;

/**
 * Service - Captcha
 * 
 */
@Service("captchaServiceImpl")
public class CaptchaServiceImpl implements CaptchaService {

	@Resource(name = "imageCaptchaService")
	private com.octo.captcha.service.CaptchaService imageCaptchaService;

	public BufferedImage buildImage(String captchaId) {
		return (BufferedImage) imageCaptchaService.getChallengeForID(captchaId);
	}

	public boolean isValid(String captchaId, String captcha) {
		if (StringUtils.isNotEmpty(captchaId)
				&& StringUtils.isNotEmpty(captcha)) {
			try {
				return imageCaptchaService.validateResponseForID(captchaId,
						captcha.toUpperCase());
			} catch (Exception e) {
				return false;
			}
		} else {
			return false;
		}
	}

}