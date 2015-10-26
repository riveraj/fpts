package edu.rit.se.fpts.util;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import edu.rit.se.fpts.Main;
import edu.rit.se.fpts.model.external.EquityRecord;

public class CSVUtil {

	public static List<EquityRecord> getEquitiesFromFile() {
		try {
			return importFromCSV(Paths.get(Main.class.getClassLoader().getResource("equities.csv").toURI()).toFile());
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static List<EquityRecord> importFromCSV(File file) {
		try {
			List<String> lines = Files.readAllLines(file.toPath(), Charset.forName("UTF-8"));
			List<String> indexed = lines.subList(0, 124);
			List<String> rest = lines.subList(124, lines.size());

			List<EquityRecord> records = new ArrayList<EquityRecord>();

			for (String line : indexed) {
				String[] split = line.split("\"");
				EquityRecord record = new EquityRecord();
				record.setSymbol(split[1]);
				record.setName(split[3]);
				record.setPrice(split[5]);
				record.setMarket(split[7]);

				if (split.length >= 9)
					record.setSecondaryIndex(split[9]);
				else
					record.setSecondaryIndex("");

				record.setSector("");
				records.add(record);
			}

			for (String line : rest) {
				String[] split = line.split("\"");
				EquityRecord record = new EquityRecord();
				record.setSymbol(split[1]);
				record.setName(split[3]);
				record.setPrice(split[5]);
				record.setMarket("");
				record.setSecondaryIndex("");
				record.setSector(split[7]);
				records.add(record);
			}

			return records;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}