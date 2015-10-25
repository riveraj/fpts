package edu.rit.se.fpts.model;

import java.time.LocalDate;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import edu.rit.se.fpts.util.LocalDateAdapter;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Transaction {

	private final StringProperty detail = new SimpleStringProperty();
	private final ObjectProperty<LocalDate> date = new SimpleObjectProperty<LocalDate>();

	@XmlElement
	public String getDetail() {
		return this.detail.get();
	}

	public void setDetail(String detail) {
		this.detail.set(detail);
	}

	public StringProperty detailProperty() {
		return this.detail;
	}

	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	public LocalDate getDate() {
		return this.date.get();
	}

	public void setDate(LocalDate date) {
		this.date.set(date);
	}

	public ObjectProperty<LocalDate> dateProperty() {
		return this.date;
	}

	@Override
	public String toString() {
		return this.detail.get();
	}
}
