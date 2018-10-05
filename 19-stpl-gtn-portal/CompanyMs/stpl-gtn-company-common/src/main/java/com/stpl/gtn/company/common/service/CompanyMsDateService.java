package com.stpl.gtn.company.common.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.stpl.gtn.company.common.constant.CompanyMsCommonConstant;

public class CompanyMsDateService {

	public static String getCurrentDateInString() {

		Date todaysDate = new Date();
		DateFormat dateFormat = new SimpleDateFormat(CompanyMsCommonConstant.DATEFORMAT_EVENTTIME);
		try {
			String str = dateFormat.format(todaysDate);
			return str;

		} catch (Exception ex) {
			System.out.println(ex);
			return "";
		}

	}
}
