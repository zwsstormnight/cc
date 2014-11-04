/*
 */
package com.copex.common.service;

import java.awt.image.BufferedImage;

/**
 * Service - Captcha
 * 
 */
public interface CaptchaService {
	/**
	 * build captcha image
	 * 
	 * @param captchaId
	 *            captach id
	 * @return captch image
	 */
	BufferedImage buildImage(String captchaId);

	/**
	 * validation captcha
	 * 
	 * @param captchaId
	 *            capcha id
	 * @param captcha
	 *            capcha, ignore case
	 * @return result of the validation
	 */
	boolean isValid(String captchaId, String captcha);

}