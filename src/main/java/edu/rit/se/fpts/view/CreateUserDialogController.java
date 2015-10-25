package edu.rit.se.fpts.view;

import edu.rit.se.fpts.controller.LoginManager;
import edu.rit.se.fpts.model.User;
import edu.rit.se.fpts.util.AlertUtil;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateUserDialogController {

	@FXML
	private TextField loginIdField;

	@FXML
	private PasswordField passwordField;

	private Stage dialogStage;
	private LoginManager manager;
	private boolean success = false;

	@FXML
	private void initialize() {
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public void setManager(LoginManager manager) {
		this.manager = manager;
	}

	public boolean success() {
		return success;
	}

	@FXML
	private void handleCreate() {
		if (validInput()) {
			User user = new User();
			user.setLoginId(loginIdField.getText());
			user.setPassword(passwordField.getText().hashCode());

			success = manager.create(user);

			if (success) {
				dialogStage.close();
			} else {
				String title = "User Creation Failed";
				String headerMessage = "An unknown error occurred.";
				String errorMessage = "Failed to create a new user. Please try again.";
				AlertUtil.showErrorAlert(dialogStage, title, headerMessage, errorMessage);
			}
		}
	}

	@FXML
	private void handleCancel() {
		dialogStage.close();
	}

	private boolean validInput() {
		String errorMessage = new String();

		if (loginIdField.getText() == null || loginIdField.getText().length() == 0)
			errorMessage += "A login ID is required.\n";

		if (passwordField.getText() == null || passwordField.getText().length() == 0)
			errorMessage += "A password is required.\n";

		if (errorMessage.length() == 0) {
			return true;
		} else {
			AlertUtil.showErrorAlert(dialogStage, "Invalid Fields", "Please correct the following errors.",
					errorMessage);
			return false;
		}
	}
}
