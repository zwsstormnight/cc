/*
 */
package com.copex.core.controller;

import java.util.Date;
import java.util.Set;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.copex.core.DateEditor;
import com.copex.core.util.SpringUtils;

/**
 * Controller Base class
 * 
 */
public class BaseController {

	private static final String CONSTRAINT_VIOLATIONS_ATTRIBUTE_NAME = "constraintViolations";

	@Resource(name = "validator")
	private Validator validator;

	/**
	 * Initialize Data Binder
	 * 
	 * @param binder
	 *            WebDataBinder
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new DateEditor(true));
	}

	/**
	 * Data Validation
	 * 
	 * @param target
	 * @param groups
	 * @return
	 */
	protected boolean isValid(Object target, Class<?>... groups) {
		Set<ConstraintViolation<Object>> constraintViolations = validator
				.validate(target, groups);
		if (constraintViolations.isEmpty()) {
			return true;
		} else {
			RequestAttributes requestAttributes = RequestContextHolder
					.currentRequestAttributes();
			requestAttributes.setAttribute(
					CONSTRAINT_VIOLATIONS_ATTRIBUTE_NAME, constraintViolations,
					RequestAttributes.SCOPE_REQUEST);
			return false;
		}
	}

	/**
	 * Data Validation
	 * 
	 * @param type
	 * @param property
	 * @param value
	 * @param groups
	 * @return
	 */
	protected boolean isValid(Class<?> type, String property, Object value,
			Class<?>... groups) {
		Set<?> constraintViolations = validator.validateValue(type, property,
				value, groups);
		if (constraintViolations.isEmpty()) {
			return true;
		} else {
			RequestAttributes requestAttributes = RequestContextHolder
					.currentRequestAttributes();
			requestAttributes.setAttribute(
					CONSTRAINT_VIOLATIONS_ATTRIBUTE_NAME, constraintViolations,
					RequestAttributes.SCOPE_REQUEST);
			return false;
		}
	}

	/**
	 * Localize Message
	 * 
	 * @param code
	 * @param args
	 * 
	 * @return
	 */
	protected String message(String code, Object... args) {
		return SpringUtils.getMessage(code, args);
	}
}