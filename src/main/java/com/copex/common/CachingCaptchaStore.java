package com.copex.common;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import com.octo.captcha.Captcha;
import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.captchastore.CaptchaAndLocale;
import com.octo.captcha.service.captchastore.CaptchaStore;

@Component("cachingCaptchaStore")
public class CachingCaptchaStore implements CaptchaStore {

	private static final String DEFAULT_CACHE_NAME = "captcha";

	@Resource(name = "cacheManager")
	private CacheManager cacheManager;

	private Set<String> keySet = new HashSet<String>();

	private Cache cache;

	@Override
	public boolean hasCaptcha(String s) {
		Object result = cache.get(s);
		return result != null;
	}

	@Override
	public void storeCaptcha(String s, Captcha captcha)
			throws CaptchaServiceException {
		captcha.getChallenge();
		cache.put(s, new CaptchaAndLocale(captcha));
		keySet.add(s);
	}

	@Override
	public void storeCaptcha(String s, Captcha captcha, Locale locale)
			throws CaptchaServiceException {
		captcha.getChallenge();
		cache.put(s, new CaptchaAndLocale(captcha, locale));
		keySet.add(s);
	}

	@Override
	public boolean removeCaptcha(String s) {
		cache.evict(s);
		return keySet.remove(s);
	}

	@Override
	public Captcha getCaptcha(String s) throws CaptchaServiceException {
		ValueWrapper result = cache.get(s);
		if (result != null) {
			CaptchaAndLocale captchaAndLocale = (CaptchaAndLocale) result.get();
			return captchaAndLocale.getCaptcha();
		}
		return null;
	}

	@Override
	public Locale getLocale(String s) throws CaptchaServiceException {
		ValueWrapper result = cache.get(s);
		if (result != null) {
			CaptchaAndLocale captchaAndLocale = (CaptchaAndLocale) result.get();
			return captchaAndLocale.getLocale();
		}
		return null;
	}

	@Override
	public int getSize() {
		return keySet.size();
	}

	@Override
	public Collection<String> getKeys() {
		return Collections.unmodifiableCollection(keySet);
	}

	@Override
	public void empty() {
		cache.clear();
		keySet.clear();
	}

	@Override
	public void initAndStart() {
		this.cache = cacheManager.getCache(DEFAULT_CACHE_NAME);
	}

	@Override
	public void cleanAndShutdown() {
		empty();
	}

}
