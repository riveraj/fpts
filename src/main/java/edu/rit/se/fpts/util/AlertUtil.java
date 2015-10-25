package edu.rit.se.fpts.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class AlertUtil {

	public static void showErrorAlert(Stage stage, String title, String headerMessage, String errorMessage) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.initOwner(stage);
		alert.setTitle(title);
		alert.setHeaderText(headerMessage);
		alert.setContentText(errorMessage);

		alert.showAndWait();
	}
}
