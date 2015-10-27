package edu.rit.se.fpts.model.external.visitor;

import java.math.BigDecimal;

import edu.rit.se.fpts.model.external.EquityRecord;
import edu.rit.se.fpts.util.YahooFinanceUtil;

public class EquityRecordVisitor implements Visitor {

	private BigDecimal DJIA = BigDecimal.ZERO;
	private BigDecimal total = BigDecimal.ZERO;

	@Override
	public void visit(EquityRecord record) {
		String price = YahooFinanceUtil.getCurrentPrice(record.getSymbol());
		record.setPrice(price);

		if (record.getMarket() == "DOW" || record.getSecondaryIndex() == "DOW") {
			DJIA.add(new BigDecimal(record.getPrice()));
			total.add(BigDecimal.ONE);
		}
	}

	public String getDJIA() {
		return DJIA.divide(total).toString();
	}
}
