package pl.pap.utils;

import java.awt.print.Book;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class Utility {
	/**
	 * Null check Method
	 *
	 * @param txt
	 * @return
	 */
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

	/**
	 * Method to construct JSON
	 *
	 * @param tag
	 * @param status
	 * @return
	 */
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

	/**
	 * Method to construct JSON with Error Msg
	 *
	 * @param tag
	 * @param status
	 * @param err_msg
	 * @return
	 */
	public static String constructJSON(String tag, boolean status,
			String errorMessage) {
		JSONObject jO = new JSONObject();
		try {
			jO.put("tag", tag);
			jO.put("status", new Boolean(status));
			jO.put("errorMessage", errorMessage);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
		}
		return jO.toString();
	}

	/**
      Method to construct JSON with data payload
	 *
	 * @param tag
	 * @param status
	 * @param data
	 * @return
     */
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
