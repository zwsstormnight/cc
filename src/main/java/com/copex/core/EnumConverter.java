/*
 */
package com.copex.core;

import org.apache.commons.beanutils.converters.AbstractConverter;

/**
 * EnumConverter
 * 
 */
public class EnumConverter extends AbstractConverter {

	private final Class<?> enumClass;

	/**
	 * @param enumClass
	 */
	public EnumConverter(Class<?> enumClass) {
		this(enumClass, null);
	}

	/**
	 * @param enumClass
	 * @param defaultValue
	 */
	public EnumConverter(Class<?> enumClass, Object defaultValue) {
		super(defaultValue);
		this.enumClass = enumClass;
	}

	@Override
	protected Class<?> getDefaultType() {
		return this.enumClass;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected Object convertToType(Class type, Object value) {
		String stringValue = value.toString().trim();
		return Enum.valueOf(type, stringValue);
	}

	protected String convertToString(Object value) {
		return value.toString();
	}

}