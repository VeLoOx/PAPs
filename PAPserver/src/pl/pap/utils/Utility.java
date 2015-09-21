package pl.pap.utils;

import java.awt.print.Book;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class Utility {
	public static boolean isNotNull(String txt) {
		// System.out.println("Inside isNotNull");
		return txt != null && txt.trim().length() > 0 ? true : false;
	}

	public static boolean isSpecialCharacter(String txt) {
		// System.out.println("Special char");
		Pattern pattern = Pattern.compile("[^A-Za-z0-9@.]");
		Matcher matcher = pattern.matcher(txt);

		return matcher.find();

	}

	public static String constructJSON(String tag, boolean status) {
		JSONObject jO = new JSONObject();
		try {
			jO.put("tag", tag);
			jO.put("status", new Boolean(status));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
		}
		return jO.toString();
	}

	public static String constructJSON(String tag, boolean status,
			String message) {
		JSONObject jO = new JSONObject();
		try {
			jO.put("tag", tag);
			jO.put("status", new Boolean(status));
			jO.put("message", message);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
		}
		return jO.toString();
	}

	public static String constructDataJSON(String tag, boolean status,
			String data) {	
		JSONObject jO = new JSONObject();
		try {
			jO.put("tag", tag);
			jO.put("status", new Boolean(status));
			jO.put("data", data);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
		}
		return jO.toString();
	}

}
