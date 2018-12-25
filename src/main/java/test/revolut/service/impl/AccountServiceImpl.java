package test.revolut.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import test.revolut.exception.RepositoryException;
import test.revolut.model.Account;
import test.revolut.repository.impl.AccountRepository;
import test.revolut.service.AccountService;

public class AccountServiceImpl implements AccountService {

	private static Logger LOG = Logger.getLogger(AccountService.class);

	@Inject
	private AccountRepository accountRepository;

	@Override
	public Account findById(Long id) {
		return accountRepository.findOne(id);
	}

	@Override
	public Account update(Account account) {

		try {
			return accountRepository.update(account);

		} catch (RepositoryException e) {
			LOG.fatal(e.getCause());

		}

		return null;
	}

	@Override
	public List<Account> findAll() {
		return accountRepository.findAll();
	}

	@Override
	public Account delete(Account account) {
		try {
			return accountRepository.delete(account);
		} catch (RepositoryException e) {
			LOG.fatal(e.getCause());
		}

		return null;
	}

}
