package edu.rit.se.fpts.model;

import javax.xml.bind.annotation.XmlElement;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Equity {

	public enum EquityType {
		BOND("Bond"), MUTUAL_FUND("Mutual Fund"), STOCK("Stock");

		private final String value;

		private EquityType(String value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return this.value;
		}
	}

	private final ObjectProperty<EquityType> type = new SimpleObjectProperty<EquityType>();
	private final StringProperty symbol = new SimpleStringProperty();
	private final IntegerProperty shares = new SimpleIntegerProperty();

	@XmlElement
	public EquityType getType() {
		return this.type.get();
	}

	public void setType(EquityType type) {
		this.type.set(type);
	}

	public ObjectProperty<EquityType> typeProperty() {
		return this.type;
	}

	@XmlElement
	public String getSymbol() {
		return this.symbol.get();
	}

	public void setSymbol(String symbol) {
		this.symbol.set(symbol);
	}

	public StringProperty symbolProperty() {
		return this.symbol;
	}

	@XmlElement
	public int getShares() {
		return this.shares.get();
	}

	public void setShares(int shares) {
		this.shares.set(shares);
	}

	public IntegerProperty sharesProperty() {
		return this.shares;
	}

	@Override
	public String toString() {
		return this.symbol.get();
	}
}
