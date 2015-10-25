package edu.rit.se.fpts.model.simulation;

import java.math.BigDecimal;

import javafx.scene.chart.XYChart.Series;

public interface SimulationStrategy {

	public Series<String, BigDecimal> simulate();
}
