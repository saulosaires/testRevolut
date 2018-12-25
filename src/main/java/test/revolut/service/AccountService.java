package test.revolut.service;

import java.util.List;

import org.jvnet.hk2.annotations.Contract;

import test.revolut.model.Account;

@Contract
public interface AccountService {

	public Account findById(Long id);

	public Account update(Account account);

	public List<Account> findAll();

	public Account delete(Account account);
	
}
