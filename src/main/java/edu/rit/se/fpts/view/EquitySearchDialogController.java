package edu.rit.se.fpts.view;

import edu.rit.se.fpts.controller.MainManager;
import edu.rit.se.fpts.model.external.EquityRecord;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EquitySearchDialogController {

	@FXML
	private TableView<EquityRecord> equityTable;

	@FXML
	private TableColumn<EquityRecord, String> equitySymbolColumn;

	@FXML
	private TableColumn<EquityRecord, String> equityNameColumn;

	@FXML
	private TableColumn<EquityRecord, String> equityPriceColumn;

	@FXML
	private TableColumn<EquityRecord, String> equityMarketColumn;

	@FXML
	private TableColumn<EquityRecord, String> equitySecondaryIndexColumn;

	@FXML
	private TableColumn<EquityRecord, String> equitySectorColumn;

	@FXML
	private TextField symbolField;

	@FXML
	private TextField nameField;

	@FXML
	private TextField indexField;

	private String symbolSearchValue = "";
	private String nameSearchValue = "";
	private String indexSearchValue = "";

	private Stage dialogStage;
	private MainManager manager;
	private boolean success = false;

	@FXML
	private void initialize() {
		equitySymbolColumn.setCellValueFactory(cellData -> cellData.getValue().symbolProperty());
		equityNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		equityPriceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty());
		equityMarketColumn.setCellValueFactory(cellData -> cellData.getValue().marketProperty());
		equitySecondaryIndexColumn.setCellValueFactory(cellData -> cellData.getValue().secondaryIndexProperty());
		equitySectorColumn.setCellValueFactory(cellData -> cellData.getValue().sectorProperty());

		equityTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> handleEquitySelection(newValue));

		symbolField.textProperty().addListener((observable, oldValue, newValue) -> handleSymbolSearch(newValue));
		nameField.textProperty().addListener((observable, oldValue, newValue) -> handleNameSearch(newValue));
		indexField.textProperty().addListener((observable, oldValue, newValue) -> handleIndexSearch(newValue));
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public void setManager(MainManager manager) {
		this.manager = manager;

		equityTable.setItems(this.manager.getEquityRecordData());
	}

	public boolean success() {
		return success;
	}

	private void handleEquitySelection(EquityRecord record) {
		manager.setEquitySearchResult(record);
		success = true;
		dialogStage.close();
	}

	private void handleSymbolSearch(String value) {
		if (value != null)
			symbolSearchValue = value.toUpperCase();
		else
			symbolSearchValue = "";

		equityTable.setItems(filter());
	}

	private void handleNameSearch(String value) {
		if (value != null)
			nameSearchValue = value.toUpperCase();
		else
			nameSearchValue = "";

		equityTable.setItems(filter());
	}

	private void handleIndexSearch(String value) {
		if (value != null)
			indexSearchValue = value.toUpperCase();
		else
			indexSearchValue = "";

		equityTable.setItems(filter());
	}

	private ObservableList<EquityRecord> filter() {
		ObservableList<EquityRecord> records = manager.getEquityRecordData();
		ObservableList<EquityRecord> result = FXCollections.observableArrayList();

		for (EquityRecord record : records)
			if (record.getSymbol().toUpperCase().contains(symbolSearchValue)
					&& record.getName().toUpperCase().contains(nameSearchValue)
					&& (record.getMarket().toUpperCase().contains(indexSearchValue)
							|| record.getSecondaryIndex().contains(indexSearchValue)))
				result.add(record);

		return result;
	}
}
