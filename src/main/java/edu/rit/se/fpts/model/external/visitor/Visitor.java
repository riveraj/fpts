package edu.rit.se.fpts.model.external.visitor;

import edu.rit.se.fpts.model.external.EquityRecord;

public interface Visitor {

	public void visit(EquityRecord record);
}
