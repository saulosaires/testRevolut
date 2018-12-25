package test.revolut.repository.impl;

import test.revolut.model.Account;

public class AccountRepository extends AbstractRepositoryImpl<Account> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1358843609602731648L;

	public AccountRepository() {
		setClazz(Account.class);

	}

}