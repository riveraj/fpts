package edu.rit.se.fpts.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class YahooFinanceUtil {

	private static final String URL_PREFIX = "http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.quotes%20where%20symbol%20in%20(%22";
	private static final String URL_POSTFIX = "%22)&env=store://datatables.org/alltableswithkeys";

	public static String getCurrentPrice(String symbol) {
		String uri = URL_PREFIX + symbol + URL_POSTFIX;

		try {
			URL url = new URL(uri);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			con.setRequestMethod("GET");
			con.setConnectTimeout(10000);
			con.setReadTimeout(10000);

			StringBuilder response = new StringBuilder();

			try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
				String inputLine;

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}

				return response.toString().split("<Ask>")[1].split("</Ask>")[0];
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
