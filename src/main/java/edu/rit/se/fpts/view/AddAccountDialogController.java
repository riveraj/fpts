package edu.rit.se.fpts.view;

import java.math.BigDecimal;
import java.time.LocalDate;

import edu.rit.se.fpts.controller.MainManager;
import edu.rit.se.fpts.controller.command.AddAccountCommand;
import edu.rit.se.fpts.controller.command.Command;
import edu.rit.se.fpts.model.Account;
import edu.rit.se.fpts.model.Account.AccountType;
import edu.rit.se.fpts.util.AlertUtil;
import edu.rit.se.fpts.util.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddAccountDialogController {

	@FXML
	private ComboBox<AccountType> typeField;

	@FXML
	private TextField nameField;

	@FXML
	private TextField amountField;

	@FXML
	private TextField dateField;

	private Stage dialogStage;
	private MainManager manager;
	private boolean success = false;

	@FXML
	private void initialize() {
		typeField.getItems().setAll(Account.AccountType.values());
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
	private void handleAdd() {
		if (validInput()) {
			Account account = new Account();
			account.setType(typeField.getValue());
			account.setName(nameField.getText());
			account.setAmount(new BigDecimal(amountField.getText()));

			LocalDate date = DateUtil.parse(dateField.getText());

			Command command = new AddAccountCommand(manager.getModel(), account, date);
			manager.execute(command);

			success = true;

			if (success) {
				dialogStage.close();
			} else {
				String title = "Cannot Add Account";
				String headerMessage = "Failed to add the account.";
				String errorMessage = "An unknown error occurred while adding the account.";
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

		if (typeField.getValue() == null)
			errorMessage += "A type is required.\n";

		if (nameField.getText() == null || nameField.getText().length() == 0)
			errorMessage += "A name is required.\n";

		if (amountField.getText() == null || amountField.getText().length() == 0)
			errorMessage += "An amount is required.\n";

		if (!DateUtil.validDate(dateField.getText()))
			errorMessage += "A date is required. Please use the format mm/dd/yyyy.";

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
