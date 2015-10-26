package edu.rit.se.fpts.model.observable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Observable;

import edu.rit.se.fpts.model.Account;
import edu.rit.se.fpts.model.Equity;
import edu.rit.se.fpts.model.Model;
import edu.rit.se.fpts.model.Portfolio;
import edu.rit.se.fpts.model.Transaction;
import edu.rit.se.fpts.model.User;
import edu.rit.se.fpts.model.WatchedEquity;

public class ModelObservable extends Observable {

	private final User user;

	public ModelObservable(Model model, int id) {
		this.user = model.getUsers().get(id);
	}

	public void setPortfolio(Portfolio portfolio) {
		this.user.setPortfolio(portfolio);
		setChanged();
		notifyObservers();
	}

	public void addAccount(Account account) {
		this.user.getPortfolio().getAccounts().add(account);
		setChanged();
		notifyObservers();
	}

	public void removeAccount(Account account) {
		this.user.getPortfolio().getAccounts().remove(account);
		setChanged();
		notifyObservers();
	}

	public void depositToAccount(BigDecimal amount, Account account) {
		int index = this.user.getPortfolio().getAccounts().indexOf(account);
		BigDecimal currentAmount = this.user.getPortfolio().getAccounts().get(index).getAmount();
		this.user.getPortfolio().getAccounts().get(index).setAmount(currentAmount.add(amount));
		setChanged();
		notifyObservers();
	}

	public void withdrawFromAccount(BigDecimal amount, Account account) {
		int index = this.user.getPortfolio().getAccounts().indexOf(account);
		BigDecimal currentAmount = this.user.getPortfolio().getAccounts().get(index).getAmount();

		if (currentAmount.compareTo(amount) == -1)
			throw new IllegalStateException();

		this.user.getPortfolio().getAccounts().get(index).setAmount(currentAmount.subtract(amount));
		setChanged();
		notifyObservers();
	}

	public void addEquity(Equity equity) {
		this.user.getPortfolio().getEquities().add(equity);
		setChanged();
		notifyObservers();
	}

	public void removeEquity(Equity equity) {
		this.user.getPortfolio().getEquities().remove(equity);
		setChanged();
		notifyObservers();
	}

	public void addTransaction(Transaction transaction) {
		this.user.getPortfolio().getTransactions().add(transaction);
		setChanged();
		notifyObservers();
	}

	public void removeLastTransaction() {
		List<Transaction> transactions = this.user.getPortfolio().getTransactions();
		transactions.remove(transactions.size() - 1);
		setChanged();
		notifyObservers();
	}

	public void addWatchedEquity(WatchedEquity watchedEquity) {
		this.user.getPortfolio().getWatchlist().add(watchedEquity);
		setChanged();
		notifyObservers();
	}

	public void removeWatchedEquity(WatchedEquity watchedEquity) {
		this.user.getPortfolio().getWatchlist().remove(watchedEquity);
	}

	public User getUser() {
		return this.user;
	}
}
