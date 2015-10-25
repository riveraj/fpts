package edu.rit.se.fpts.controller.command;

import java.math.BigDecimal;
import java.time.LocalDate;

import edu.rit.se.fpts.model.Account;
import edu.rit.se.fpts.model.Transaction;
import edu.rit.se.fpts.model.observable.ModelObservable;

public class AccountWithdrawalCommand implements Command {

	private final ModelObservable model;
	private final Account account;
	private final BigDecimal amount;

	public AccountWithdrawalCommand(ModelObservable model, Account account, BigDecimal amount) {
		this.model = model;
		this.account = account;
		this.amount = amount;
	}

	@Override
	public void execute() {
		model.withdrawFromAccount(amount, account);

		Transaction transaction = new Transaction();
		String detail = "$" + amount + " withdrew from " + account;
		transaction.setDetail(detail);
		transaction.setDate(LocalDate.now());
		model.addTransaction(transaction);
	}

	@Override
	public void undo() {
		model.depositToAccount(amount, account);
		model.removeLastTransaction();
	}
}
