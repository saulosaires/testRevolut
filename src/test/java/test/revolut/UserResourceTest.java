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

public class UserResourceTest {

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
	public void testcreateUser() {

		User user = new User();

		user.setUserName("user12");
		user.setEmailAddress("email12");

		Account account = new Account();
		account.setBalance(new BigDecimal(10));

		user.setAccount(account);

		Response response = createUser(user);

		assertEquals(response.getStatus(), Response.Status.OK.getStatusCode());

		User userSaved = response.readEntity(User.class);

		Response responseDelete = deleteUser(userSaved);

		assertEquals(responseDelete.getStatus(), Response.Status.OK.getStatusCode());
	}

	@Test
	public void testgetUserByName() {

		User user = new User();

		user.setUserName("userName");
		user.setEmailAddress("email1");

		Account account = new Account();
		account.setBalance(new BigDecimal(10));

		user.setAccount(account);

		Response response = createUser(user);

		assertEquals(response.getStatus(), Response.Status.OK.getStatusCode());

		Response responseGetUserByName = getUserByName(user.getUserName());

		assertEquals(Response.Status.OK.getStatusCode(), responseGetUserByName.getStatus());

		User userReturned = responseGetUserByName.readEntity(User.class);

		assertEquals("userName", userReturned.getUserName());

		deleteUser(userReturned);
	}

	@Test
	public void testUpdateUser() {

		User user = new User();

		user.setUserName("userNameMa");
		user.setEmailAddress("email1@gmail.com");

		Account account = new Account();
		account.setBalance(new BigDecimal(10));

		user.setAccount(account);

		Response response = createUser(user);

		assertEquals(response.getStatus(), Response.Status.OK.getStatusCode());

		User userSaved = response.readEntity(User.class);

		User newUser = new User();
		
		newUser.setUserName("userNamePI");
		newUser.setEmailAddress("email2@gmail.com");

		Response responseUserUpdated = updateUser(userSaved.getId(),newUser);

		assertEquals(Response.Status.OK.getStatusCode(), responseUserUpdated.getStatus());

		User userUpdated = responseUserUpdated.readEntity(User.class);

		assertEquals("userNamePI", userUpdated.getUserName());
		assertEquals("email2@gmail.com", userUpdated.getEmailAddress());

		deleteUser(userUpdated);

	}

	@Test
	public void testDeleteUser() {

		User user = new User();

		user.setUserName("testDeleteUser");
		user.setEmailAddress("testDeleteUser");

		Account account = new Account();
		account.setBalance(new BigDecimal(10));

		user.setAccount(account);

		Response response = createUser(user);

		assertEquals(response.getStatus(), Response.Status.OK.getStatusCode());

		User userSaved = response.readEntity(User.class);

		Response responseDelete = deleteUser(userSaved);

		assertEquals(responseDelete.getStatus(), Response.Status.OK.getStatusCode());
		
		Response responseGetUserByName = getUserByName(user.getUserName());
		
		assertEquals(Response.Status.NOT_FOUND.getStatusCode(), responseGetUserByName.getStatus());

 
}
	
	private Response createUser(User user) {
		return target.path("user/").request().post(Entity.json(user));

	}

	private Response deleteUser(User user) {
		return target.path("user/" + user.getId()).request().delete();

	}

	private Response getUserByName(String userName) {
		return target.path("user/" + userName).request().get();

	}

	private Response updateUser(Long id,User user) {
		return target.path("user/" + id).request().put(Entity.json(user));

	}

}
