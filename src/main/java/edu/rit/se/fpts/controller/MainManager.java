package edu.rit.se.fpts.controller;

import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import edu.rit.se.fpts.Main;
import edu.rit.se.fpts.controller.command.Command;
import edu.rit.se.fpts.controller.command.Invoker;
import edu.rit.se.fpts.model.Account;
import edu.rit.se.fpts.model.Equity;
import edu.rit.se.fpts.model.Transaction;
import edu.rit.se.fpts.model.observable.ModelObservable;
import edu.rit.se.fpts.util.DataPersistenceUtil;
import edu.rit.se.fpts.view.AccountDepositDialogController;
import edu.rit.se.fpts.view.AccountTransferDialogController;
import edu.rit.se.fpts.view.AccountWithdrawalDialogController;
import edu.rit.se.fpts.view.AddAccountDialogController;
import edu.rit.se.fpts.view.AddEquityDialogController;
import edu.rit.se.fpts.view.MainViewController;
import edu.rit.se.fpts.view.MarketSimulationDialogController;
import edu.rit.se.fpts.view.RemoveEquityDialogController;
import edu.rit.se.fpts.view.RootLayoutController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainManager implements Manager {

	private final Stage primaryStage;
	private final ModelObservable model;
	private final ObservableList<Account> accountData = FXCollections.observableArrayList();
	private final ObservableList<Equity> equityData = FXCollections.observableArrayList();
	private final ObservableList<Transaction> transactionData = FXCollections.observableArrayList();
	private final Invoker invoker = new Invoker();

	private BorderPane rootLayout;

	public MainManager(Stage primaryStage, ModelObservable model) {
		this.primaryStage = primaryStage;
		this.model = model;
	}

	@Override
	public void init() {
		accountData.addAll(model.getUser().getPortfolio().getAccounts());
		equityData.addAll(model.getUser().getPortfolio().getEquities());
		transactionData.addAll(model.getUser().getPortfolio().getTransactions());

		model.addObserver(new Observer() {
			@Override
			public void update(Observable obs, Object obj) {
				accountData.clear();
				accountData.addAll(model.getUser().getPortfolio().getAccounts());
			}
		});

		model.addObserver(new Observer() {
			@Override
			public void update(Observable observable, Object object) {
				equityData.clear();
				equityData.addAll(model.getUser().getPortfolio().getEquities());
			}
		});

		model.addObserver(new Observer() {
			@Override
			public void update(Observable observable, Object object) {
				transactionData.clear();
				transactionData.addAll(model.getUser().getPortfolio().getTransactions());
			}
		});

		initRootLayout();
		showMainView();
	}

	public void exportPortfolio(File file) {
		DataPersistenceUtil.Mode mode = DataPersistenceUtil.Mode.XML;
		DataPersistenceUtil.serialize(this.model.getUser().getPortfolio(), file, mode);
	}

	public ModelObservable getModel() {
		return this.model;
	}

	public ObservableList<Account> getAccountData() {
		return this.accountData;
	}

	public ObservableList<Equity> getEquityData() {
		return this.equityData;
	}

	public ObservableList<Transaction> getTransactionData() {
		return this.transactionData;
	}

	public void execute(Command command) {
		this.invoker.executeCommand(command);
	}

	public void undo() {
		this.invoker.undoCommand();
	}

	public void redo() {
		this.invoker.redoCommand();
	}

	public void initRootLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();

			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);

			RootLayoutController controller = loader.getController();
			controller.setPrimaryStage(this.primaryStage);
			controller.setManager(this);

			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showMainView() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/MainView.fxml"));
			AnchorPane mainView = (AnchorPane) loader.load();

			rootLayout.setCenter(mainView);

			MainViewController controller = loader.getController();
			controller.setManager(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean showAddAccountDialog() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/AddAccountDialog.fxml"));
			AnchorPane pane = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Add an Account");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(pane);
			dialogStage.setScene(scene);

			AddAccountDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setManager(this);
			dialogStage.showAndWait();
			return controller.success();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean showAccountDepositDialog() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/AccountDepositDialog.fxml"));
			AnchorPane pane = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Account Deposit");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(pane);
			dialogStage.setScene(scene);

			AccountDepositDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setManager(this);
			dialogStage.showAndWait();
			return controller.success();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean showAccountWithdrawalDialog() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/AccountWithdrawalDialog.fxml"));
			AnchorPane pane = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Account Withdrawal");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(pane);
			dialogStage.setScene(scene);

			AccountWithdrawalDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setManager(this);
			dialogStage.showAndWait();
			return controller.success();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean showAccountTransferDialog() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/AccountTransferDialog.fxml"));
			AnchorPane pane = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Transfer Funds Between Accounts");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(pane);
			dialogStage.setScene(scene);

			AccountTransferDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setManager(this);
			dialogStage.showAndWait();
			return controller.success();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean showAddEquityDialog() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/AddEquityDialog.fxml"));
			AnchorPane pane = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Add an Equity");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(pane);
			dialogStage.setScene(scene);

			AddEquityDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setManager(this);
			dialogStage.showAndWait();
			return controller.success();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean showRemoveEquityDialog() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/RemoveEquityDialog.fxml"));
			AnchorPane pane = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Remove an Equity");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(pane);
			dialogStage.setScene(scene);

			RemoveEquityDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setManager(this);
			dialogStage.showAndWait();
			return controller.success();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean showEquitySearchDialog() {
		return false;
	}

	public boolean showMarketSimulationDialog() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/MarketSimulationDialog.fxml"));
			AnchorPane pane = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Market Simulation");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(pane);
			dialogStage.setScene(scene);

			MarketSimulationDialogController controller = loader.getController();
			dialogStage.showAndWait();
			return controller.success();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}
