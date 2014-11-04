package com.copex.core.transaction;

import javax.persistence.EntityManagerFactory;

import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.TransactionDefinition;

import com.copex.core.datasource.AvailableDataSources;
import com.copex.core.datasource.DatasourceProvider;

/**
 *  ReadWriteStatusAwareJpaTransactionManager
 *
 */
@SuppressWarnings("serial")
public class ReadWriteStatusAwareJpaTransactionManager extends JpaTransactionManager {

	public ReadWriteStatusAwareJpaTransactionManager() {
		super();
	}

	public ReadWriteStatusAwareJpaTransactionManager(EntityManagerFactory emf) {
		super(emf);
	}

	@Override
	protected void doBegin(Object transaction, TransactionDefinition definition) {
		DatasourceProvider
				.setDatasource(definition.isReadOnly() ? AvailableDataSources.READ
						: AvailableDataSources.WRITE);
		super.doBegin(transaction, definition);
	}

	@Override
	protected void doCleanupAfterCompletion(Object transaction) {
		DatasourceProvider.clearDatasource();
		super.doCleanupAfterCompletion(transaction);
	}

}
