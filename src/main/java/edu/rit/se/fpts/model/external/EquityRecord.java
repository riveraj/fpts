package edu.rit.se.fpts.model.external;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EquityRecord {

	private final StringProperty symbol = new SimpleStringProperty();
	private final StringProperty name = new SimpleStringProperty();
	private final StringProperty price = new SimpleStringProperty();
	private final StringProperty market = new SimpleStringProperty();
	private final StringProperty secondaryIndex = new SimpleStringProperty();
	private final StringProperty sector = new SimpleStringProperty();

	public String getSymbol() {
		return this.symbol.get();
	}

	public void setSymbol(String symbol) {
		this.symbol.set(symbol);
	}

	public StringProperty symbolProperty() {
		return this.symbol;
	}

	public String getName() {
		return this.name.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public StringProperty nameProperty() {
		return this.name;
	}

	public String getPrice() {
		return this.price.get();
	}

	public void setPrice(String price) {
		this.price.set(price);
	}

	public StringProperty priceProperty() {
		return this.price;
	}

	public String getMarket() {
		return this.market.get();
	}

	public void setMarket(String market) {
		this.market.set(market);
	}

	public StringProperty marketProperty() {
		return this.market;
	}

	public String getSecondaryIndex() {
		return this.secondaryIndex.get();
	}

	public void setSecondaryIndex(String secondaryIndex) {
		this.secondaryIndex.set(secondaryIndex);
	}

	public StringProperty secondaryIndexProperty() {
		return this.secondaryIndex;
	}

	public String getSector() {
		return this.sector.get();
	}

	public void setSector(String sector) {
		this.sector.set(sector);
	}

	public StringProperty sectorProperty() {
		return this.sector;
	}
}
