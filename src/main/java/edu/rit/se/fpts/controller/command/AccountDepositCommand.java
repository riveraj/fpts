package edu.rit.se.fpts.controller.command;

import java.math.BigDecimal;
import java.time.LocalDate;

import edu.rit.se.fpts.model.Account;
import edu.rit.se.fpts.model.Transaction;
import edu.rit.se.fpts.model.observable.ModelObservable;

public class AccountDepositCommand implements Command {

	private final ModelObservable model;
	private final Account account;
	private final BigDecimal amount;

	public AccountDepositCommand(ModelObservable model, Account account, BigDecimal amount) {
		this.model = model;
		this.account = account;
		this.amount = amount;
	}

	@Override
	public void execute() {
		model.depositToAccount(amount, account);

		Transaction transaction = new Transaction();
		String detail = "$" + amount + " deposited into " + account;
		transaction.setDetail(detail);
		transaction.setDate(LocalDate.now());
		model.addTransaction(transaction);
	}

	@Override
	public void undo() {
		model.withdrawFromAccount(amount, account);
		model.removeLastTransaction();
	}
}
