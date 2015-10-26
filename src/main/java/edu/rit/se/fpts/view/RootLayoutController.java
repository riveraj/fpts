package edu.rit.se.fpts.view;

import java.io.File;

import edu.rit.se.fpts.controller.MainManager;
import edu.rit.se.fpts.controller.command.Command;
import edu.rit.se.fpts.controller.command.ImportPortfolioCommand;
import edu.rit.se.fpts.model.Portfolio;
import edu.rit.se.fpts.util.DataPersistenceUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class RootLayoutController {

	private Stage primaryStage;
	private MainManager manager;

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public void setManager(MainManager manager) {
		this.manager = manager;
	}

	@FXML
	private void handleSave() {
		manager.save();
	}

	@FXML
	private void handleImport() {
		FileChooser fileChooser = new FileChooser();

		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);

		File file = fileChooser.showOpenDialog(primaryStage);

		if (file != null) {
			DataPersistenceUtil.Mode mode = DataPersistenceUtil.Mode.XML;
			Portfolio portfolio = (Portfolio) DataPersistenceUtil.deserialize(Portfolio.class, file, mode);
			Command command = new ImportPortfolioCommand(manager.getModel(), portfolio);

			manager.execute(command);
		}
	}

	@FXML
	private void handleExport() {
		FileChooser fileChooser = new FileChooser();

		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);

		File file = fileChooser.showSaveDialog(primaryStage);

		if (file != null) {
			if (!file.getPath().endsWith(".xml"))
				file = new File(file.getPath(), ".xml");

			manager.exportPortfolio(file);
		}
	}

	@FXML
	private void handleLogout() {
		manager.logout();
	}

	@FXML
	private void handleUndo() {
		manager.undo();
	}

	@FXML
	private void handleRedo() {
		manager.redo();
	}

	@FXML
	private void handleSimulate() {
		manager.showMarketSimulationDialog();
	}

	@FXML
	private void handleAbout() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("About...");
		alert.setHeaderText("About Financial Portfolios Tracking System (FPTS)");
		alert.setContentText("Author: John Rivera\nFor the SWEN-262 Class Project.");

		alert.showAndWait();
	}
}
