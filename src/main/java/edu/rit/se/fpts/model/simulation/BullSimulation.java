package edu.rit.se.fpts.model.simulation;

import java.math.BigDecimal;
import java.util.List;

import edu.rit.se.fpts.model.Equity;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;

public class BullSimulation implements SimulationStrategy {

	private final static BigDecimal GROWTH_MULTIPLIER = BigDecimal.valueOf(2.0);

	private final BigDecimal initialPrice;
	private final List<String> intervals;

	public BullSimulation(Equity equity, List<String> intervals) {
		this.initialPrice = BigDecimal.TEN;
		this.intervals = intervals;
	}

	@Override
	public Series<String, BigDecimal> simulate() {
		Series<String, BigDecimal> series = new Series<String, BigDecimal>();
		BigDecimal price = initialPrice;

		for (String interval : intervals) {
			series.getData().add(new Data<String, BigDecimal>(interval, price));
			price = price.multiply(GROWTH_MULTIPLIER);
		}

		return series;
	}
}
