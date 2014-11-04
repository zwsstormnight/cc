package com.copex.core.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class ReadWriteRoutingDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		AvailableDataSources ads = DatasourceProvider.getDatasource();

		String key = "WRITE";
		if (ads != null && ads.equals(AvailableDataSources.READ)) {
			key = "READ";
		}
		return key;
	}

}
