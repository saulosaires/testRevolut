package test.revolut.resource;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import test.revolut.exception.BusinessException;
import test.revolut.model.Account;
import test.revolut.model.Transaction;
import test.revolut.service.AccountService;
import test.revolut.service.TransactionService;
import test.revolut.utils.Util;

@Path("/transaction")
@Produces(MediaType.APPLICATION_JSON)
public class TransactionResource {

	public static final String INVALID_TRANSACTION="Invalid transaction object";
	public static final String INVALID_AMOUNT="Invalid Amount value";
	public static final String INVALID_SOURCE_ACCOUNT="Invalid Source Account";
	public static final String INVALID_DESTINATION_ACCOUNT="Invalid Destination Account";
	public static final String FROM_ACCOUNT_NOT_FOUND="Source Account not found";
	public static final String TO_ACCOUNT_NOT_FOUND="Destination Accountnot found";
	public static final String FROM_ACCOUNT_INSUFFICIENT_FUNDS="Insufficient Funds";
	
	private static Logger LOGGER = Logger.getLogger(TransactionResource.class);

	@Inject
	private TransactionService transactionService;

	@Inject
	private AccountService accountService;

	/**
	 * Transfer fund between two accounts.
	 * 
	 * @param transaction
	 * @return
	 * @throws BusinessException
	 */
	@POST
	public Response transferFund(Transaction transaction) throws BusinessException {

		validate(transaction);

		Account accountFrom = accountService.findById(transaction.getAccountFrom().getId());
		Account accountTo = accountService.findById(transaction.getAccountTo().getId());

		transaction.setAccountFrom(accountFrom);
		transaction.setAccountTo(accountTo);

		try {
			Transaction t = transactionService.transfer(transaction);

			if (t != null)
				return Response.status(Response.Status.OK).build();

			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();

		} catch (Exception e) {
			LOGGER.error("m=transferFund error=" + e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}

	}

	private void validate(Transaction transaction) throws BusinessException {

		if (transaction == null) {
			LOGGER.error("m=transferFund error="+INVALID_TRANSACTION);
			throw new BusinessException(INVALID_TRANSACTION);
		}

		if (transaction.getAmount() == null || transaction.getAmount().doubleValue() <= 0) {
			LOGGER.error("m=transferFund error="+INVALID_AMOUNT);
			throw new BusinessException(INVALID_AMOUNT);
		}

		if (transaction.getAccountFrom() == null || !Util.validId(transaction.getAccountFrom().getId())) {
			LOGGER.error("m=transferFund error="+INVALID_SOURCE_ACCOUNT);
			throw new BusinessException(INVALID_SOURCE_ACCOUNT);
		}

		Account accountFrom = accountService.findById(transaction.getAccountFrom().getId());
		if (accountFrom == null) {
			LOGGER.error("m=transferFund error="+FROM_ACCOUNT_NOT_FOUND);
			throw new BusinessException(FROM_ACCOUNT_NOT_FOUND);
		}
		
		if (accountFrom.getBalance().compareTo(transaction.getAmount())<0) {
			LOGGER.error("m=transferFund error="+FROM_ACCOUNT_INSUFFICIENT_FUNDS);
			throw new BusinessException(FROM_ACCOUNT_INSUFFICIENT_FUNDS);
		}
		
		
		if (transaction.getAccountTo() == null || !Util.validId(transaction.getAccountTo().getId())) {
			LOGGER.error("m=transferFund error="+INVALID_DESTINATION_ACCOUNT);
			throw new BusinessException(INVALID_DESTINATION_ACCOUNT);
		}
		
		Account accountTo = accountService.findById(transaction.getAccountTo().getId());
		if (accountTo == null) {
			LOGGER.error("m=transferFund error="+TO_ACCOUNT_NOT_FOUND);
			throw new BusinessException(TO_ACCOUNT_NOT_FOUND);
		}
		

	}

}
