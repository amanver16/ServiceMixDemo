package com.stpl.hr.login.common.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoginMsDateService {

	public static String getCurrentDateInString() {

		Date todaysDate = new Date();
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		try {
			String str = dateFormat.format(todaysDate);
			return str;

		} catch (Exception ex) {
			System.out.println("LoginMs: Common: DateConversion Error" + ex);
			return "";
		}

	}
}
