package edu.rit.se.fpts.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Portfolio {

	private List<Account> accounts;
	private List<Equity> equities;
	private List<Transaction> transactions;
	private List<WatchedEquity> watchlist;

	@XmlElement(name = "account")
	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

	@XmlElement(name = "equity")
	public List<Equity> getEquities() {
		return equities;
	}

	public void setEquities(List<Equity> equities) {
		this.equities = equities;
	}

	@XmlElement(name = "transaction")
	public List<Transaction> getTransactions() {
		return this.transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	@XmlElement(name = "watchlist")
	public List<WatchedEquity> getWatchlist() {
		return this.watchlist;
	}

	public void setWatchlist(List<WatchedEquity> watchlist) {
		this.watchlist = watchlist;
	}
}
