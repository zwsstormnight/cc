/*
 */
package com.copex.common.service.impl;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.copex.common.service.CacheService;

/**
 * Service Impl - Cache
 * 
 */
@Service("cacheServiceImpl")
public class CacheServiceImpl implements CacheService {

	@Resource(name = "messageSource")
	private ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource;
	@Resource(name = "freeMarkerConfigurer")
	private FreeMarkerConfigurer freeMarkerConfigurer;

	@CacheEvict(value = { "setting" }, allEntries = true)
	public void clear() {
		reloadableResourceBundleMessageSource.clearCache();
		freeMarkerConfigurer.getConfiguration().clearTemplateCache();
	}

}