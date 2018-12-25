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
import test.revolut.model.User;

public class AccountResourceTest {

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
	public void testGetAccount() {

		User user = new User();

		user.setUserName("user1");
		user.setEmailAddress("email1");

		Account account = new Account();
		account.setBalance(new BigDecimal(10));

		user.setAccount(account);

		user = createUser(user);

		Response response = getAccount(user.getAccount().getId());

		assertEquals(response.getStatus(), Response.Status.OK.getStatusCode());

		Account accountRetrieved = response.readEntity(Account.class);

		assertEquals(new BigDecimal(10), accountRetrieved.getBalance());

		deleteUser(user);

	}

	@Test
	public void testDepositAccount() {
		
		User user = new User();

		user.setUserName("user2");
		user.setEmailAddress("email2");

		Account account = new Account();
		account.setBalance(new BigDecimal(10));

		user.setAccount(account);

		user = createUser(user);
		
		Response response =deposit(user.getAccount().getId(),new BigDecimal(3));
		
		assertEquals(response.getStatus(), Response.Status.OK.getStatusCode());
		
		account = response.readEntity(Account.class);
		
		assertEquals(new BigDecimal(13),account.getBalance());
		deleteUser(user);
	}
	
	@Test
	public void testDepositAccountInvalidId() {
		
		Response response =deposit(-1L,new BigDecimal(3));
		
		assertEquals(response.getStatus(), Response.Status.NOT_FOUND.getStatusCode());

	}
	
	@Test
	public void testDepositAccountInvalidAmount() {
		
		User user = new User();

		user.setUserName("user55");
		user.setEmailAddress("email55");

		Account account = new Account();
		account.setBalance(new BigDecimal(10));

		user.setAccount(account);

		user = createUser(user);
		
		Response response =deposit(user.getAccount().getId(),new BigDecimal(-3));
		
		assertEquals(response.getStatus(), Response.Status.BAD_REQUEST.getStatusCode());
		
		deleteUser(user);
	}
	
	
	@Test
	public void testWithdrawAccount() {
		
		User user = new User();

		user.setUserName("user3");
		user.setEmailAddress("email3");

		Account account = new Account();
		account.setBalance(new BigDecimal(10));

		user.setAccount(account);

		user = createUser(user);
		
		Response response =withdraw(user.getAccount().getId(),new BigDecimal(3));
		
		assertEquals(response.getStatus(), Response.Status.OK.getStatusCode());
		
		account = response.readEntity(Account.class);
		
		assertEquals(new BigDecimal(7),account.getBalance());
		
		deleteUser(user);
	}	
	
	@Test
	public void testWithdrawtAccountInvalidId() {
		
		Response response =withdraw(-1L,new BigDecimal(3));
		
		assertEquals(response.getStatus(), Response.Status.NOT_FOUND.getStatusCode());

	}
	
	@Test
	public void testWithdrawAccountInvalidAmount() {
		
		User user = new User();

		user.setUserName("user55");
		user.setEmailAddress("email55");

		Account account = new Account();
		account.setBalance(new BigDecimal(10));

		user.setAccount(account);

		user = createUser(user);
		
		Response response =withdraw(user.getAccount().getId(),new BigDecimal(-3));
		
		assertEquals(response.getStatus(), Response.Status.BAD_REQUEST.getStatusCode());
		
		deleteUser(user);
	}
	
	@Test
	public void testWithdrawAccountInsufficientFunds() {
		
		User user = new User();

		user.setUserName("user55");
		user.setEmailAddress("email55");

		Account account = new Account();
		account.setBalance(new BigDecimal(10));

		user.setAccount(account);

		user = createUser(user);
		
		Response response =withdraw(user.getAccount().getId(),new BigDecimal(30));
		
		assertEquals(response.getStatus(), Response.Status.BAD_REQUEST.getStatusCode());
		
		deleteUser(user);
	}
	
	
	private User createUser(User user) {

		Response response = target.path("user/").request().post(Entity.json(user));
		return response.readEntity(User.class);

	}

	private Response deleteUser(User user) {
		return target.path("user/" + user.getId()).request().delete();

	}
	
	private Response getAccount(Long accountId) {

		return target.path("account/" + accountId).request().get();

	}

   private Response deposit(Long accountId,BigDecimal amount) {
	   return target.path("account/"+accountId+"/deposit/"+amount).request().put(Entity.json(""));
   }
 
   private Response withdraw(Long accountId,BigDecimal amount) {
	   return target.path("account/"+accountId+"/withdraw/"+amount).request().put(Entity.json(""));
   }

}
