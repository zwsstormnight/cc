package com.copex.core;

public interface Staticizable {
	/**
	 * Get static id in template.xml
	 * 
	 * @return
	 */
	String getStaticId();

	/**
	 * Get static path in template.xml
	 * 
	 * @return
	 */
	String getPath();

	/**
	 * Get the object key used in template
	 * 
	 * 
	 * @return
	 */

	String getObjectKey();
}
