package com.stpl.hr.onboard.common.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.stpl.hr.onboard.common.constant.OnboardMsCommonConstant;

public class OnboardMsDateService {

	public static String getCurrentDateInString() {

		Date todaysDate = new Date();
		DateFormat dateFormat = new SimpleDateFormat(OnboardMsCommonConstant.DATAFORMAT_EVENTTIME);
		try {
			String str = dateFormat.format(todaysDate);
			return str;

		} catch (Exception ex) {
			System.out.println(ex);
			return "";
		}

	}
}
