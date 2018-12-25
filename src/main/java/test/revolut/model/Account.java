package test.revolut.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Entity
@Table(name = "ACCOUNT")
public class Account implements Serializable {

	private static Logger LOGGER = Logger.getLogger(Account.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private BigDecimal balance;

	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	public Account() {
	}

	public Account(User user, BigDecimal balance) {
		this.user = user;
		this.balance = balance;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public BigDecimal debit(BigDecimal amount) {
		this.balance =this.balance.subtract(amount);
		return getBalance();
	}
	
	public BigDecimal credit(BigDecimal amount) {
		this.balance =this.balance.add(amount);
		return getBalance();
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Account account = (Account) o;

		if (id != account.id)
			return false;
		if (!user.equals(account.user))
			return false;
		if (!balance.equals(account.balance))
			return false;
		return true;

	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(id).append(user).append(balance).toHashCode();
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
