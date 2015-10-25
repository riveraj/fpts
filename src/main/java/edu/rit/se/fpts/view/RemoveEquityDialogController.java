package edu.rit.se.fpts.view;

import java.math.BigDecimal;
import java.time.LocalDate;

import edu.rit.se.fpts.controller.MainManager;
import edu.rit.se.fpts.controller.command.Command;
import edu.rit.se.fpts.controller.command.RemoveEquityCommand;
import edu.rit.se.fpts.model.Account;
import edu.rit.se.fpts.model.Equity;
import edu.rit.se.fpts.util.AlertUtil;
import edu.rit.se.fpts.util.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RemoveEquityDialogController {

	@FXML
	private ComboBox<Equity> equityField;

	@FXML
	private ComboBox<Account> accountField;

	@FXML
	private TextField sharesField;

	@FXML
	private TextField priceField;

	@FXML
	private TextField dateField;

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

		equityField.getItems().addAll(manager.getEquityData());
		accountField.getItems().setAll(manager.getAccountData());
	}

	public boolean success() {
		return success;
	}

	@FXML
	private void handleRemove() {
		if (validInput()) {
			Equity equity = new Equity();
			equity.setType(equityField.getValue().getType());
			equity.setSymbol(equityField.getValue().getSymbol());
			equity.setShares(Integer.parseInt(sharesField.getText()));

			BigDecimal price = new BigDecimal(priceField.getText());
			Account account = accountField.getValue();
			LocalDate date = DateUtil.parse(dateField.getText());

			Command command = new RemoveEquityCommand(manager.getModel(), equity, account, price, date);
			manager.execute(command);

			success = true;

			if (success) {
				dialogStage.close();
			} else {
				String title = "Cannot Add Equity";
				String headerMessage = "Failed to add the equity.";
				String errorMessage = "An unknown error occurred while adding the equity.";
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

		if (equityField.getValue() == null)
			errorMessage += "An equity is required.\n";

		if (sharesField.getText() == null || sharesField.getText().length() == 0)
			errorMessage += "The number of shares is required.\n";

		if (priceField.getText() == null || priceField.getText().length() == 0)
			errorMessage += "The price sold is required.\n";

		if (!DateUtil.validDate(dateField.getText()))
			errorMessage += "A date is required. Please use the format mm/dd/yyyy.";

		if (errorMessage.length() == 0) {
			return true;
		} else {
			AlertUtil.showErrorAlert(dialogStage, "Invalid Fields", "Please correct the following errors.",
					errorMessage);
			return false;
		}
	}
}
