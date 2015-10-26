package edu.rit.se.fpts.view;

import java.math.BigDecimal;
import java.time.LocalDate;

import edu.rit.se.fpts.controller.MainManager;
import edu.rit.se.fpts.model.Account;
import edu.rit.se.fpts.model.Equity;
import edu.rit.se.fpts.model.Transaction;
import edu.rit.se.fpts.model.WatchedEquity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
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
	private TableView<WatchedEquity> watchlistTable;

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
	private TableColumn<WatchedEquity, String> watchlistSymbolColumn;

	@FXML
	private TableColumn<WatchedEquity, String> watchlistNameColumn;

	@FXML
	private TableColumn<WatchedEquity, BigDecimal> watchlistPriceColumn;

	@FXML
	private DatePicker transactionFromDateField;

	@FXML
	private DatePicker transactionToDateField;

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

	private LocalDate fromDateSearchValue;
	private LocalDate toDateSearchValue;

	private MainManager manager;

	@FXML
	private void initialize() {
		accountNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		accountTypeColumn.setCellValueFactory(cellData -> cellData.getValue().typeProperty());

		equitySymbolColumn.setCellValueFactory(cellData -> cellData.getValue().symbolProperty());
		equityTypeColumn.setCellValueFactory(cellData -> cellData.getValue().typeProperty());

		transactionDetailColumn.setCellValueFactory(cellData -> cellData.getValue().detailProperty());
		transactionDateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());

		watchlistSymbolColumn.setCellValueFactory(cellData -> cellData.getValue().symbolProperty());
		watchlistNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		watchlistPriceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty());

		showAccountDetails(null);
		showEquityDetails(null);

		accountTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showAccountDetails(newValue));
		equityTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showEquityDetails(newValue));

		transactionFromDateField.valueProperty()
				.addListener((Observable, oldValue, newValue) -> handleFilterFromDate(newValue));

		transactionToDateField.valueProperty()
				.addListener((Observable, oldValue, newValue) -> handleFilterToDate(newValue));
	}

	public void setManager(MainManager manager) {
		this.manager = manager;

		accountTable.setItems(manager.getAccountData());
		equityTable.setItems(manager.getEquityData());
		transactionTable.setItems(manager.getTransactionData());
		watchlistTable.setItems(manager.getWatchlistData());
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

	public void handleDeleteEquityFromWatchlist() {

	}

	public void handleAddEquityToWatchlist() {

	}

	public void handleEditEquityInWatchlist() {

	}

	public void handleAddDowJonesIndustrialAverageToWatchlist() {
		WatchedEquity equity = new WatchedEquity();
		equity.setSymbol("DOW");
		equity.setName("Dow Jones Industrial Average");
		equity.setPrice(BigDecimal.TEN);
		manager.getModel().addWatchedEquity(equity);
	}

	public void handleFilterFromDate(LocalDate value) {
		if (value != null)
			fromDateSearchValue = value;
		else
			fromDateSearchValue = LocalDate.MIN;

		transactionTable.setItems(filter());
	}

	public void handleFilterToDate(LocalDate value) {
		if (value != null)
			toDateSearchValue = value;
		else
			toDateSearchValue = LocalDate.MAX;

		transactionTable.setItems(filter());
	}

	private ObservableList<Transaction> filter() {
		ObservableList<Transaction> transactions = manager.getTransactionData();
		ObservableList<Transaction> result = FXCollections.observableArrayList();

		for (Transaction transaction : transactions)
			if (transaction.getDate().isAfter(fromDateSearchValue) && transaction.getDate().isBefore(toDateSearchValue))
				result.add(transaction);

		return result;
	}
}
