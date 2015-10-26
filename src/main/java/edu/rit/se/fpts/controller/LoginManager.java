package edu.rit.se.fpts.controller;

import java.io.IOException;
import java.util.ArrayList;

import edu.rit.se.fpts.Main;
import edu.rit.se.fpts.model.Account;
import edu.rit.se.fpts.model.Equity;
import edu.rit.se.fpts.model.Model;
import edu.rit.se.fpts.model.Portfolio;
import edu.rit.se.fpts.model.Transaction;
import edu.rit.se.fpts.model.User;
import edu.rit.se.fpts.model.WatchedEquity;
import edu.rit.se.fpts.model.observable.ModelObservable;
import edu.rit.se.fpts.util.DataPersistenceUtil;
import edu.rit.se.fpts.view.CreateUserDialogController;
import edu.rit.se.fpts.view.LoginDialogController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LoginManager implements Manager {

	private final Stage primaryStage;
	private final Model model;

	public LoginManager(Stage primaryStage, Model model) {
		this.primaryStage = primaryStage;
		this.model = model;
	}

	@Override
	public void init() {
		showLoginDialog();
	}

	public boolean login(User user) {
		if (!this.model.getUsers().contains(user))
			return false;

		int index = this.model.getUsers().indexOf(user);
		initPortfolio(index);
		ModelObservable modelObservable = new ModelObservable(model, index);
		Manager manager = new MainManager(primaryStage, modelObservable);
		manager.init();

		return true;
	}

	public boolean create(User user) {
		if (this.model.getUsers().contains(user))
			return false;

		this.model.getUsers().add(user);
		DataPersistenceUtil.saveModelToDataFile(model);

		return true;
	}

	public boolean showLoginDialog() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/LoginDialog.fxml"));
			AnchorPane pane = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Login to FPTS");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(pane);
			dialogStage.setScene(scene);
			LoginDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setManager(this);
			dialogStage.showAndWait();
			return controller.success();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean showCreateUserDialog() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/CreateUserDialog.fxml"));
			AnchorPane pane = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Create a New User");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(pane);
			dialogStage.setScene(scene);
			CreateUserDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setManager(this);
			dialogStage.showAndWait();
			return controller.success();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	private void initPortfolio(int id) {
		User user = model.getUsers().get(id);

		if (user.getPortfolio() == null)
			user.setPortfolio(new Portfolio());

		if (user.getPortfolio().getAccounts() == null)
			user.getPortfolio().setAccounts(new ArrayList<Account>());

		if (user.getPortfolio().getEquities() == null)
			user.getPortfolio().setEquities(new ArrayList<Equity>());

		if (user.getPortfolio().getTransactions() == null)
			user.getPortfolio().setTransactions(new ArrayList<Transaction>());

		if (user.getPortfolio().getWatchlist() == null)
			user.getPortfolio().setWatchlist(new ArrayList<WatchedEquity>());
	}
}
