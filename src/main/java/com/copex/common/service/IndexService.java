package com.copex.common.service;

import java.io.Serializable;

public interface IndexService<T, ID extends Serializable> {
	public void addToIndex(T entry);

	public void deleteFromIndex(ID id);
	
	public void updateToIndex(T entry);
}
