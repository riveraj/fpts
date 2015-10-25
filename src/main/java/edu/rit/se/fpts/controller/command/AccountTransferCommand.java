package edu.rit.se.fpts.controller.command;

import java.math.BigDecimal;
import java.time.LocalDate;

import edu.rit.se.fpts.model.Account;
import edu.rit.se.fpts.model.Transaction;
import edu.rit.se.fpts.model.observable.ModelObservable;

public class AccountTransferCommand implements Command {

	private final ModelObservable model;
	private final Account from;
	private final Account to;
	private final BigDecimal amount;

	public AccountTransferCommand(ModelObservable model, Account from, Account to, BigDecimal amount) {
		this.model = model;
		this.from = from;
		this.to = to;
		this.amount = amount;
	}

	@Override
	public void execute() {
		model.withdrawFromAccount(amount, from);
		model.depositToAccount(amount, to);

		Transaction transaction = new Transaction();
		String detail = "$" + amount + " transferred from " + from + " to " + to;
		transaction.setDetail(detail);
		transaction.setDate(LocalDate.now());
		model.addTransaction(transaction);
	}

	@Override
	public void undo() {
		model.depositToAccount(amount, from);
		model.withdrawFromAccount(amount, to);
		model.removeLastTransaction();
	}

}
