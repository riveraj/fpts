package edu.rit.se.fpts.model;

import javax.xml.bind.annotation.XmlElement;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User {

	private final StringProperty loginId = new SimpleStringProperty();
	private final IntegerProperty password = new SimpleIntegerProperty();

	private Portfolio portfolio;

	@XmlElement
	public String getLoginId() {
		return loginId.get();
	}

	public void setLoginId(String loginId) {
		this.loginId.set(loginId);
	}

	public StringProperty loginIdProperty() {
		return loginId;
	}

	@XmlElement
	public int getPassword() {
		return password.get();
	}

	public void setPassword(int password) {
		this.password.set(password);
	}

	public IntegerProperty passwordProperty() {
		return password;
	}

	@XmlElement
	public Portfolio getPortfolio() {
		return this.portfolio;
	}

	public void setPortfolio(Portfolio portfolio) {
		this.portfolio = portfolio;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((loginId.get() == null) ? 0 : loginId.get().hashCode());
		result = prime * result + password.get();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (loginId == null) {
			if (other.loginId != null)
				return false;
		} else if (!loginId.get().equals(other.loginId.get()))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (password.get() != other.password.get())
			return false;
		return true;
	}
}
