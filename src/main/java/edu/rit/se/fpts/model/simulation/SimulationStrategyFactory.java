package edu.rit.se.fpts.model.simulation;

import java.util.List;

import edu.rit.se.fpts.model.Equity;

public class SimulationStrategyFactory {

	public enum Strategy {
		NO_GROWTH, BULL, BEAR;
	}

	public static SimulationStrategy getSimulation(Equity equity, List<String> intervals, Strategy strategy) {
		switch (strategy) {
		case NO_GROWTH:
			return new NoGrowthSimulation(equity, intervals);
		case BULL:
			return new BullSimulation(equity, intervals);
		case BEAR:
			return new BearSimulation(equity, intervals);
		default:
			throw new IllegalArgumentException();
		}
	}
}
