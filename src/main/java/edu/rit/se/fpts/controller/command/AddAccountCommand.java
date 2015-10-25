package edu.rit.se.fpts.controller.command;

import java.time.LocalDate;

import edu.rit.se.fpts.model.Account;
import edu.rit.se.fpts.model.Transaction;
import edu.rit.se.fpts.model.observable.ModelObservable;

public class AddAccountCommand implements Command {

	private final ModelObservable model;
	private final Account account;
	private final LocalDate date;

	public AddAccountCommand(ModelObservable model, Account account, LocalDate date) {
		this.model = model;
		this.account = account;
		this.date = date;
	}

	@Override
	public void execute() {
		model.addAccount(account);

		Transaction transaction = new Transaction();
		String detail = account + " added";
		transaction.setDetail(detail);
		transaction.setDate(date);
		model.addTransaction(transaction);
	}

	@Override
	public void undo() {
		model.removeAccount(account);
		model.removeLastTransaction();
	}
}
