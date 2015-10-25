package edu.rit.se.fpts.view;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import edu.rit.se.fpts.model.Equity;
import edu.rit.se.fpts.model.simulation.SimulationStrategy;
import edu.rit.se.fpts.model.simulation.SimulationStrategyFactory;
import edu.rit.se.fpts.model.simulation.SimulationStrategyFactory.Strategy;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;

public class MarketSimulationDialogController {

	@FXML
	private LineChart<String, BigDecimal> noGrowthChart;

	@FXML
	private LineChart<String, BigDecimal> bullChart;

	@FXML
	private LineChart<String, BigDecimal> bearChart;

	@FXML
	private CategoryAxis noGrowthXAxis;

	@FXML
	private CategoryAxis bullXAxis;

	@FXML
	private CategoryAxis bearXAxis;

	private Equity equity;
	private boolean success = false;

	@FXML
	private void initialize() {
		String[] values = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
		List<String> intervals = Arrays.asList(values);
		ObservableList<String> monthNames = FXCollections.observableArrayList();
		monthNames.addAll(intervals);

		noGrowthXAxis.setCategories(monthNames);
		bullXAxis.setCategories(monthNames);
		bearXAxis.setCategories(monthNames);
	}

	public void setEquity(Equity equity) {
		this.equity = equity;

		setMarketData();
	}

	public boolean success() {
		return success;
	}

	public void setMarketData() {
		String[] values = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
		List<String> intervals = Arrays.asList(values);

		SimulationStrategy noGrowth = SimulationStrategyFactory.getSimulation(equity, intervals, Strategy.NO_GROWTH);
		SimulationStrategy bull = SimulationStrategyFactory.getSimulation(equity, intervals, Strategy.BULL);
		SimulationStrategy bear = SimulationStrategyFactory.getSimulation(equity, intervals, Strategy.BEAR);

		noGrowthChart.getData().add(noGrowth.simulate());
		bullChart.getData().add(bull.simulate());
		bearChart.getData().add(bear.simulate());
	}
}
