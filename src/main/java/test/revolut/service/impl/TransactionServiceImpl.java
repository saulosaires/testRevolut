package test.revolut.service.impl;

import java.math.BigDecimal;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.apache.log4j.Logger;
import org.jvnet.hk2.annotations.Service;

import test.revolut.exception.BusinessException;
import test.revolut.exception.RepositoryException;
import test.revolut.model.Account;
import test.revolut.model.Transaction;
import test.revolut.repository.impl.TransactionRepository;
import test.revolut.service.AccountService;
import test.revolut.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService {

	private static Logger LOG = Logger.getLogger(TransactionService.class);

	@Inject
	private TransactionRepository transactionRepository;

	@Inject
	AccountService accountService;

	@Override
	@Transactional
	public Transaction transfer(Transaction transaction) throws BusinessException {

		try {
			Account accountFrom = accountService.findById(transaction.getAccountFrom().getId());
			Account accountTo = accountService.findById(transaction.getAccountTo().getId());

			accountFrom.debit(transaction.getAmount());
			accountTo.credit(transaction.getAmount());

			updateAccount(accountFrom);
			updateAccount(accountTo);

			return createTransaction(accountFrom,accountTo,transaction.getAmount());
 

		} catch (RepositoryException e) {
			LOG.fatal(e.getCause());

		}

		return null;
	}
	
	@Transactional(value=TxType.REQUIRES_NEW)
	private Account updateAccount(Account account) {
		return accountService.update(account);
	}
	
	@Transactional(value=TxType.REQUIRES_NEW)
	private Transaction createTransaction(Account accountFrom,Account accountTo,BigDecimal amount) throws RepositoryException {
		
		Transaction t = new Transaction();
		
		t.setAmount(amount);
		t.setAccountFrom(accountFrom);
		t.setAccountTo(accountTo);
		
		return transactionRepository.create(t);
	}

}
