package com.stpl.hr.asset.common.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.stpl.hr.asset.common.constant.AssetMsCommonConstant;

public class AssetMsDateService {

	public static String getCurrentDateInString() {

		Date todaysDate = new Date();
		DateFormat dateFormat = new SimpleDateFormat(AssetMsCommonConstant.DATEFORMAT_EVENTTIME);
		try {
			String str = dateFormat.format(todaysDate);
			return str;

		} catch (Exception ex) {
			System.out.println(ex);
			return "";
		}

	}
}
