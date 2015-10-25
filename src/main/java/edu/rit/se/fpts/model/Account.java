package edu.rit.se.fpts.model;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlElement;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Account {

	public enum AccountType {
		BANK_ACCOUNT("Bank Account"), MONEY_MARKET("Money Market");

		private final String value;

		private AccountType(String value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return this.value;
		}
	}

	private final ObjectProperty<AccountType> type = new SimpleObjectProperty<AccountType>();
	private final StringProperty name = new SimpleStringProperty();
	private final ObjectProperty<BigDecimal> amount = new SimpleObjectProperty<BigDecimal>();

	@XmlElement
	public AccountType getType() {
		return this.type.get();
	}

	public void setType(AccountType type) {
		this.type.set(type);
	}

	public ObjectProperty<AccountType> typeProperty() {
		return this.type;
	}

	@XmlElement
	public String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public StringProperty nameProperty() {
		return this.name;
	}

	@XmlElement
	public BigDecimal getAmount() {
		return this.amount.get();
	}

	public void setAmount(BigDecimal amount) {
		this.amount.set(amount);
	}

	public ObjectProperty<BigDecimal> amountProperty() {
		return this.amount;
	}

	@Override
	public String toString() {
		return this.name.get();
	}
}
