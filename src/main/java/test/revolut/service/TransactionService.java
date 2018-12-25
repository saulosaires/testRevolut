package test.revolut.service;

import org.jvnet.hk2.annotations.Contract;

import test.revolut.exception.BusinessException;
import test.revolut.model.Transaction;

@Contract
public interface TransactionService {

	public Transaction transfer(Transaction transaction)throws BusinessException;
	
}
