package edu.rit.se.fpts.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import edu.rit.se.fpts.model.external.EquityRecord;

public class CSVUtil {

	public static List<EquityRecord> importFromCSV(File file) {
		try {
			List<String> lines = Files.readAllLines(file.toPath(), Charset.forName("UTF-8"));

			List<EquityRecord> records = new ArrayList<EquityRecord>();

			for (String line : lines) {
				String[] tmp = line.split("\"");
				EquityRecord record = new EquityRecord();
				record.setSymbol(tmp[1]);
				record.setName(tmp[3]);
				record.setPrice(tmp[5]);
				record.setMarket(tmp[7]);

				if (tmp.length >= 9)
					record.setOpt(tmp[9]);
				else
					record.setOpt("");

				records.add(record);
			}

			return records;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}