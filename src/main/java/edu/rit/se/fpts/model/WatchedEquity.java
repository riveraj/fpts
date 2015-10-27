package edu.rit.se.fpts.model;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlElement;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class WatchedEquity {

	private final StringProperty symbol = new SimpleStringProperty();
	private final ObjectProperty<BigDecimal> lowTrigger = new SimpleObjectProperty<BigDecimal>();
	private final ObjectProperty<BigDecimal> highTrigger = new SimpleObjectProperty<BigDecimal>();

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
	public BigDecimal getLowTrigger() {
		return this.lowTrigger.get();
	}

	public void setLowTrigger(BigDecimal lowTrigger) {
		this.lowTrigger.set(lowTrigger);
	}

	public ObjectProperty<BigDecimal> lowTriggerProperty() {
		return this.lowTrigger;
	}

	@XmlElement
	public BigDecimal getHighTrigger() {
		return this.highTrigger.get();
	}

	public void setHighTrigger(BigDecimal highTrigger) {
		this.highTrigger.set(highTrigger);
	}

	public ObjectProperty<BigDecimal> highTriggerProperty() {
		return this.highTrigger;
	}
}
