package edu.rit.se.fpts.view;

import edu.rit.se.fpts.controller.MainManager;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class SaveDialogController {

	private Stage dialogStage;
	private MainManager manager;
	private boolean success = false;

	@FXML
	private void initialize() {
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public void setManager(MainManager manager) {
		this.manager = manager;
	}

	public boolean success() {
		return success;
	}

	@FXML
	private void handleSave() {
		manager.save();
		success = true;
		dialogStage.close();
	}

	@FXML
	private void handleDoNotSave() {
		success = true;
		dialogStage.close();
	}

	@FXML
	private void handleCancel() {
		success = false;
		dialogStage.close();
	}
}
