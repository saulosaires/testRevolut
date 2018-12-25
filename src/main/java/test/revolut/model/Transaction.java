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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Entity
@Table(name = "TRANSACTION")
public class Transaction implements Serializable {

	private static Logger LOGGER = Logger.getLogger(Transaction.class);
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private BigDecimal amount;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "from_account_id")
	private Account accountFrom;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "to_account_id")
	private Account accountTo;

	public Transaction() {
	}

	public Transaction(BigDecimal amount, Account accountFrom, Account accountTo) {
		super();
		this.amount = amount;
		this.accountFrom = accountFrom;
		this.accountTo = accountTo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Account getAccountFrom() {
		return accountFrom;
	}

	public void setAccountFrom(Account accountFrom) {
		this.accountFrom = accountFrom;
	}

	public Account getAccountTo() {
		return accountTo;
	}

	public void setAccountTo(Account accountTo) {
		this.accountTo = accountTo;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Transaction that = (Transaction) o;

		if (!amount.equals(that.amount))
			return false;
		if (!accountFrom.equals(that.accountFrom))
			return false;
		return accountTo.equals(that.accountTo);

	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(id).append(amount).append(accountFrom).append(accountTo).toHashCode();
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
