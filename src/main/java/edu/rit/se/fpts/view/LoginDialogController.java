package edu.rit.se.fpts.view;

import edu.rit.se.fpts.controller.LoginManager;
import edu.rit.se.fpts.model.User;
import edu.rit.se.fpts.util.AlertUtil;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginDialogController {

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
	private void handleLogin() {
		if (validInput()) {
			User user = new User();
			user.setLoginId(loginIdField.getText());
			user.setPassword(passwordField.getText().hashCode());

			success = manager.login(user);

			if (success) {
				dialogStage.close();
			} else {
				String title = "Invalid Login";
				String headerMessage = "Please try again.";
				String errorMessage = "The entered login ID and/or password is invalid.";
				AlertUtil.showErrorAlert(dialogStage, title, headerMessage, errorMessage);
			}
		}
	}

	@FXML
	private void handleCreate() {
		manager.showCreateUserDialog();
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
			String title = "Invalid Fields";
			String headerMessage = "Please correct the following errors.";
			AlertUtil.showErrorAlert(dialogStage, title, headerMessage, errorMessage);
			return false;
		}
	}
}
