package edu.rit.se.fpts.view;

import java.math.BigDecimal;
import java.time.LocalDate;

import edu.rit.se.fpts.controller.MainManager;
import edu.rit.se.fpts.controller.command.AddEquityCommand;
import edu.rit.se.fpts.controller.command.Command;
import edu.rit.se.fpts.model.Account;
import edu.rit.se.fpts.model.Equity;
import edu.rit.se.fpts.model.Equity.EquityType;
import edu.rit.se.fpts.model.external.EquityRecord;
import edu.rit.se.fpts.util.AlertUtil;
import edu.rit.se.fpts.util.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddEquityDialogController {

	@FXML
	private ComboBox<EquityType> typeField;

	@FXML
	private ComboBox<Account> accountField;

	@FXML
	private TextField symbolField;

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
		typeField.getItems().setAll(Equity.EquityType.values());
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public void setManager(MainManager manager) {
		this.manager = manager;

		accountField.getItems().setAll(manager.getAccountData());
	}

	public boolean success() {
		return success;
	}

	@FXML
	private void handleAdd() {
		if (validInput()) {
			Equity equity = new Equity();
			equity.setType(typeField.getValue());
			equity.setSymbol(symbolField.getText());
			equity.setShares(Integer.parseInt(sharesField.getText()));

			BigDecimal price = null;

			if (priceField.getText() != null || priceField.getText().length() != 0)
				price = new BigDecimal(priceField.getText());

			Account account = accountField.getValue();

			LocalDate date = DateUtil.parse(dateField.getText());

			Command command = new AddEquityCommand(manager.getModel(), equity, account, price, date);
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
	private void handleEquitySearch() {
		if (manager.showEquitySearchDialog())
			populateFields(manager.getEquitySearchResult());
	}

	@FXML
	private void handleCancel() {
		dialogStage.close();
	}

	private void populateFields(EquityRecord record) {
		symbolField.setText(record.getSymbol());
		priceField.setText(record.getPrice());
	}

	private boolean validInput() {
		String errorMessage = new String();

		if (typeField.getValue() == null)
			errorMessage += "A type is required.\n";

		if (symbolField.getText() == null || symbolField.getText().length() == 0)
			errorMessage += "A symbol is required. Click the '...' button to search.\n";

		if (sharesField.getText() == null || sharesField.getText().length() == 0)
			errorMessage += "The number of shares is required.\n";

		if (!DateUtil.validDate(dateField.getText()))
			errorMessage += "A date is required. Please use the format dd.mm.yyyy.";

		if (errorMessage.length() == 0) {
			return true;
		} else {
			AlertUtil.showErrorAlert(dialogStage, "Invalid Fields", "Please correct the following errors.",
					errorMessage);
			return false;
		}
	}
}
