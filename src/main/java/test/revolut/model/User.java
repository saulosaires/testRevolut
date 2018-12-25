package test.revolut.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Entity
@Table(name = "USER")
public class User implements Serializable{

	private static Logger LOGGER = Logger.getLogger(User.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String userName;

	private String emailAddress;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, optional = false)
  private Account account;
	
	
	public User() {
	}

	public User(String userName, String emailAddress) {
		this.userName = userName;
		this.emailAddress = emailAddress;
	}

	public User(long id, String userName, String emailAddress) {
		this.id = id;
		this.userName = userName;
		this.emailAddress = emailAddress;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public Account getAccount() {
		return account;
	}

	public void updateValues(String userName, String emailAddress) {
		
		setUserName(userName);
		setEmailAddress(emailAddress);
	}
	
	public void setAccount(Account account) {
		this.account = account;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		User user = (User) o;

		if (id != user.id)
			return false;
		if (!userName.equals(user.userName))
			return false;
		return emailAddress.equals(user.emailAddress);

	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(id).append(userName).append(emailAddress).toHashCode();
	}

	@Override
	public String toString() {
        
        try {
			return new ObjectMapper().writeValueAsString(this);
			 
		} catch (JsonProcessingException e) {
			LOGGER.error(e.getMessage());
		}
        
        return null;
	}
}
