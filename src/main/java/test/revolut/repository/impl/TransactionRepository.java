package test.revolut.repository.impl;

import test.revolut.model.Transaction;

public class TransactionRepository extends AbstractRepositoryImpl<Transaction> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1358843609602731648L;

	public TransactionRepository() {
		setClazz(Transaction.class);

	}

}