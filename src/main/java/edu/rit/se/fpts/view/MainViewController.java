package edu.rit.se.fpts.view;

import java.time.LocalDate;

import edu.rit.se.fpts.controller.MainManager;
import edu.rit.se.fpts.model.Account;
import edu.rit.se.fpts.model.Equity;
import edu.rit.se.fpts.model.Transaction;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class MainViewController {

	@FXML
	private TableView<Account> accountTable;

	@FXML
	private TableView<Equity> equityTable;

	@FXML
	private TableView<Transaction> transactionTable;

	@FXML
	private TableColumn<Account, String> accountNameColumn;

	@FXML
	private TableColumn<Account, Account.AccountType> accountTypeColumn;

	@FXML
	private TableColumn<Equity, String> equitySymbolColumn;

	@FXML
	private TableColumn<Equity, Equity.EquityType> equityTypeColumn;

	@FXML
	private TableColumn<Transaction, String> transactionDetailColumn;

	@FXML
	private TableColumn<Transaction, LocalDate> transactionDateColumn;

	@FXML
	private Label accountTypeLabel;

	@FXML
	private Label accountNameLabel;

	@FXML
	private Label accountAmountLabel;

	@FXML
	private Label equityTypeLabel;

	@FXML
	private Label equitySymbolLabel;

	@FXML
	private Label equityNameLabel;

	@FXML
	private Label equitySharesLabel;

	@FXML
	private Label equityPriceLabel;

	@FXML
	private Label equityTotalValueLabel;

	private MainManager manager;

	public MainViewController() {
	}

	@FXML
	private void initialize() {
		accountNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		accountTypeColumn.setCellValueFactory(cellData -> cellData.getValue().typeProperty());

		equitySymbolColumn.setCellValueFactory(cellData -> cellData.getValue().symbolProperty());
		equityTypeColumn.setCellValueFactory(cellData -> cellData.getValue().typeProperty());

		transactionDetailColumn.setCellValueFactory(cellData -> cellData.getValue().detailProperty());
		transactionDateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());

		showAccountDetails(null);
		showEquityDetails(null);

		accountTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showAccountDetails(newValue));
		equityTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showEquityDetails(newValue));

	}

	public void setManager(MainManager manager) {
		this.manager = manager;

		accountTable.setItems(manager.getAccountData());
		equityTable.setItems(manager.getEquityData());
		transactionTable.setItems(manager.getTransactionData());
	}

	public void showAccountDetails(Account account) {
		if (account != null) {
			accountTypeLabel.setText(account.getType().toString());
			accountNameLabel.setText(account.getName());
			accountAmountLabel.setText(account.getAmount().toString());
		} else {
			accountTypeLabel.setText("");
			accountNameLabel.setText("");
			accountAmountLabel.setText("");
		}
	}

	public void showEquityDetails(Equity equity) {
		if (equity != null) {
			equityTypeLabel.setText(equity.getType().toString());
			equitySymbolLabel.setText(equity.getSymbol());
			equitySharesLabel.setText(String.valueOf(equity.getShares()));
		} else {
			equityTypeLabel.setText("");
			equitySymbolLabel.setText("");
			equitySharesLabel.setText("");
		}
	}

	public void handleAddAccount() {
		manager.showAddAccountDialog();
	}

	public void handleMakeDeposit() {
		manager.showAccountDepositDialog();
	}

	public void handleMakeWithdrawal() {
		manager.showAccountWithdrawalDialog();
	}

	public void handleMakeTransfer() {
		manager.showAccountTransferDialog();
	}

	public void handleAddEquity() {
		manager.showAddEquityDialog();
	}

	public void handleRemoveEquity() {
		manager.showRemoveEquityDialog();
	}
}
