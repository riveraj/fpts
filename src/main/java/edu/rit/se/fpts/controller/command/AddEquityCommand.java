package edu.rit.se.fpts.controller.command;

import java.math.BigDecimal;
import java.time.LocalDate;

import edu.rit.se.fpts.model.Account;
import edu.rit.se.fpts.model.Equity;
import edu.rit.se.fpts.model.Transaction;
import edu.rit.se.fpts.model.observable.ModelObservable;

public class AddEquityCommand implements Command {

	private final ModelObservable model;
	private final Equity equity;
	private final Account account;
	private final BigDecimal price;
	private final LocalDate date;

	public AddEquityCommand(ModelObservable model, Equity equity, Account account, BigDecimal price, LocalDate date) {
		this.model = model;
		this.equity = equity;
		this.account = account;
		this.price = price;
		this.date = date;
	}

	@Override
	public void execute() {
		model.addEquity(equity);

		if (account != null)
			model.withdrawFromAccount(price, account);

		Transaction transaction = new Transaction();
		String detail = equity.getShares() + " shares of " + equity + " bought for $" + price + " per share";
		transaction.setDetail(detail);
		transaction.setDate(date);
		model.addTransaction(transaction);
	}

	@Override
	public void undo() {
		model.removeEquity(equity);

		if (account != null)
			model.depositToAccount(price, account);

		model.removeLastTransaction();
	}
}
