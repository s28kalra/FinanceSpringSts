package com.lti.sms;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import javax.net.ssl.HttpsURLConnection;

import org.springframework.stereotype.Component;

@Component
public class SmsService {
	String apiKey = "NQnBE6K2WjeuZvOwloIXUSDbpAHmysf39hYF1r4GdcViJ8C0qg2klpDwMcIYT8ndPgV54FNzyQSZsuet";
	String senderId = "SMSIND";

	public boolean sendRegisterSms(String customerFirstName, String number) {
		String apiKey = "NQnBE6K2WjeuZvOwloIXUSDbpAHmysf39hYF1r4GdcViJ8C0qg2klpDwMcIYT8ndPgV54FNzyQSZsuet";
		String senderId = "SMSIND";
		String message = "ThankYou " + customerFirstName + " for registering with Easy Credit\n"
				+ "Hope you do not find any Inconvienence";
		try {

			message = URLEncoder.encode(message, "UTF-8");

			String myUrl = "https://www.fast2sms.com/dev/bulk?authorization=" + this.apiKey + "&sender_id=" + this.senderId
					+ "&message=" + message + "&language=english&route=p&numbers=" + number;

			System.out.println(myUrl);

			URL url = new URL(myUrl);

			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

			conn.setRequestMethod("GET");

			conn.setRequestProperty("cache-control", "no-cache");

			int code = conn.getResponseCode();

			StringBuffer response = new StringBuffer();

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			while (true) {

				String line = br.readLine();
				if (line == null)
					break;
				response.append(line);
			}

			return true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}

}
