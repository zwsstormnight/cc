/*
 */
package com.copex.core;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

/**
 * Date Type Conveter
 * 
 */
public class DateEditor extends PropertyEditorSupport {

	private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	private boolean emptyAsNull;

	private String dateFormat = DEFAULT_DATE_FORMAT;

	/**
	 * @param emptyAsNull
	 */
	public DateEditor(boolean emptyAsNull) {
		this.emptyAsNull = emptyAsNull;
	}

	/**
	 * @param emptyAsNull
	 * @param dateFormat
	 */
	public DateEditor(boolean emptyAsNull, String dateFormat) {
		this.emptyAsNull = emptyAsNull;
		this.dateFormat = dateFormat;
	}

	@Override
	public String getAsText() {
		Date value = (Date) getValue();
		return value != null ? new SimpleDateFormat(dateFormat).format(value)
				: "";
	}

	@Override
	public void setAsText(String text) {
		if (text == null) {
			setValue(null);
		} else {
			String value = text.trim();
			if (emptyAsNull && "".equals(value)) {
				setValue(null);
			} else {
				try {
					setValue(DateUtils.parseDate(value,
							CoreAttributes.DATE_PATTERNS));
				} catch (ParseException e) {
					setValue(null);
				}
			}
		}
	}

}