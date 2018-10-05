package com.stpl.gtn.item.common.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.stpl.gtn.item.common.constant.ItemMsCommonConstant;

public class ItemMsDateService {

	public static String getCurrentDateInString() {

		Date todaysDate = new Date();
		DateFormat dateFormat = new SimpleDateFormat(ItemMsCommonConstant.DATEFORMAT_EVENTTIME);
		try {
			String str = dateFormat.format(todaysDate);
			return str;

		} catch (Exception ex) {
			System.out.println(ex);
			return "";
		}

	}
}
