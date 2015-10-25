package edu.rit.se.fpts.controller.command;

import edu.rit.se.fpts.model.Portfolio;
import edu.rit.se.fpts.model.observable.ModelObservable;

public class ImportPortfolioCommand implements Command {

	private final ModelObservable model;
	private final Portfolio newPortfolio;
	private final Portfolio oldPortfolio;

	public ImportPortfolioCommand(ModelObservable model, Portfolio newPortfolio) {
		this.model = model;
		this.newPortfolio = newPortfolio;
		this.oldPortfolio = model.getUser().getPortfolio();
	}

	@Override
	public void execute() {
		this.model.setPortfolio(newPortfolio);
	}

	@Override
	public void undo() {
		this.model.setPortfolio(oldPortfolio);
	}
}
