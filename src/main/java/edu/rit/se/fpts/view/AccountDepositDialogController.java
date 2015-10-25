package edu.rit.se.fpts.view;

import java.math.BigDecimal;

import edu.rit.se.fpts.controller.MainManager;
import edu.rit.se.fpts.controller.command.AccountDepositCommand;
import edu.rit.se.fpts.controller.command.Command;
import edu.rit.se.fpts.model.Account;
import edu.rit.se.fpts.util.AlertUtil;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AccountDepositDialogController {

	@FXML
	private ComboBox<Account> accountField;

	@FXML
	private TextField amountField;

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

		accountField.getItems().addAll(manager.getAccountData());
	}

	public boolean success() {
		return success;
	}

	@FXML
	private void handleMakeDeposit() {
		if (validInput()) {
			Account account = accountField.getValue();
			BigDecimal amount = new BigDecimal(amountField.getText());

			Command command = new AccountDepositCommand(manager.getModel(), account, amount);
			manager.execute(command);

			success = true;

			if (success) {
				dialogStage.close();
			} else {
				String title = "Cannot Deposit to Account";
				String headerMessage = "Failed to deposit to the account.";
				String errorMessage = "An unknown error occurred while depositing to the account.";
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

		if (amountField.getText() == null || amountField.getText().length() == 0)
			errorMessage += "An amount is required.\n";

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
