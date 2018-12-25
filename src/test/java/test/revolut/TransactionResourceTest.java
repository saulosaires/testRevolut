package test.revolut;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import test.revolut.model.Account;
import test.revolut.model.Transaction;
import test.revolut.model.User;

public class TransactionResourceTest {

	private HttpServer server;
	private WebTarget target;

	@Before
	public void setUp() throws Exception {

		server = Main.startServer();

		Client c = ClientBuilder.newClient();

		target = c.target(Main.BASE_URI);
	}

	@After
	public void tearDown() throws Exception {
		server.shutdownNow();
	}

	@Test
	public void transferFund() {

		User user1 = new User();

		user1.setUserName("user1");
		user1.setEmailAddress("email1");

		Account account = new Account();
		account.setBalance(new BigDecimal(17));

		user1.setAccount(account);

		user1 = createUser(user1);

		User user2 = new User();

		user2.setUserName("user2");
		user2.setEmailAddress("email2");

		Account account2 = new Account();
		account2.setBalance(new BigDecimal(36));

		user2.setAccount(account2);

		user2 = createUser(user2);

		Transaction transaction = new Transaction();

		transaction.setAmount(new BigDecimal(5));
		transaction.setAccountFrom(user2.getAccount());
		transaction.setAccountTo(user1.getAccount());

		Response response = transferFund(transaction);

		assertEquals(response.getStatus(), Response.Status.OK.getStatusCode());

		User userRetrieved1 = getUserByName("user1");
		User userRetrieved2 = getUserByName("user2");

		assertEquals(new BigDecimal(31), userRetrieved2.getAccount().getBalance());
		assertEquals(new BigDecimal(22), userRetrieved1.getAccount().getBalance());

		deleteUser(user1);
		deleteUser(user2);

	}

	private Response transferFund(Transaction transaction) {
		return target.path("transaction/").request().post(Entity.json(transaction));
	}

	private User createUser(User user) {
		Response response = target.path("user/").request().post(Entity.json(user));

		return response.readEntity(User.class);

	}

	private User getUserByName(String userName) {
		Response response = target.path("user/" + userName).request().get();

		return response.readEntity(User.class);

	}

	private Response deleteUser(User user) {
		return target.path("user/" + user.getId()).request().delete();

	}

}
