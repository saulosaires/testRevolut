package test.revolut.resource;

import java.math.BigDecimal;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import test.revolut.exception.BusinessException;
import test.revolut.model.Account;
import test.revolut.service.AccountService;


@Path("/account")
@Produces(MediaType.APPLICATION_JSON)
public class AccountResource {

	@Inject
	AccountService accountService;

	private static Logger LOG = Logger.getLogger(AccountResource.class);
 

	/**
	 * Find account by id
	 * 
	 * @param accountId
	 * @return
	 * @throws BusinessException
	 */
	@GET
	@Path("/{accountId}")
	public Response getAccount(@PathParam("accountId") Long accountId) throws BusinessException {

		Account account = accountService.findById(accountId);

		if (account == null) {
			LOG.error("m=getAccount error=not_found");
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		return Response.status(Response.Status.OK).entity(account.toString()).build();

	}

	/**
	 * Deposit amount by account Id
	 * 
	 * @param accountId
	 * @param amount
	 * @return
	 * @throws BusinessException
	 */
	@PUT
	@Path("/{accountId}/deposit/{amount}")
	public Response deposit(@PathParam("accountId") Long accountId, @PathParam("amount") BigDecimal amount) throws BusinessException {

		Account account = accountService.findById(accountId);

		if (account == null) {
			LOG.error("m=deposit error=not_found");
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		if (amount.compareTo(BigDecimal.ZERO) <= 0) {
			LOG.error("m=deposit error=Invalid Deposit amount");
			return Response.status(Response.Status.BAD_REQUEST).entity("Invalid Deposit amount").build();
		}

		account.credit(amount);

		account = accountService.update(account);

		return Response.status(Response.Status.OK).entity(account.toString()).build();
	}

	/**
	 * Withdraw amount by account Id
	 * 
	 * @param accountId
	 * @param amount
	 * @return
	 * @throws BusinessException
	 */
	@PUT
	@Path("/{accountId}/withdraw/{amount}")
	public Response withdraw(@PathParam("accountId") long accountId, @PathParam("amount") BigDecimal amount) throws BusinessException {

		Account account = accountService.findById(accountId);

		if (account == null) {
			LOG.error("m=withdraw error=not_found");
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		if (amount.compareTo(BigDecimal.ZERO) <= 0) {
			LOG.error("m=withdraw error=Invalid withdraw amount");
			return Response.status(Response.Status.BAD_REQUEST).entity("Invalid withdraw amount").build();
		}

		if (account.getBalance().compareTo(amount) < 0) {
			LOG.error("m=withdraw error=Insufficient Funds");
			return Response.status(Response.Status.BAD_REQUEST).entity("Insufficient Funds").build();
		}

		account.debit(amount);

		account = accountService.update(account);

		return Response.status(Response.Status.OK).entity(account.toString()).build();
	}

	/**
	 * Delete account byId
	 * 
	 * @param accountId
	 * @param amount
	 * @return
	 * @throws BusinessException
	 */
	@DELETE
	@Path("/{accountId}")
	public Response deleteAccount(@PathParam("accountId") long accountId) throws BusinessException {

		Account account = accountService.findById(accountId);

		if (account == null) {
			LOG.error("m=deleteAccount error=not_found");
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		account = accountService.delete(account);

		return Response.status(Response.Status.OK).entity(account.toString()).build();

	}

}
