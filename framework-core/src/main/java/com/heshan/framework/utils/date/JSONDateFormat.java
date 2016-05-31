package com.heshan.framework.utils.date;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

/**
 * 日期格式化基于 joda-time
 * 
 * @version : Ver 1.0
 */
public class JSONDateFormat extends DateFormat {

	private static final long serialVersionUID = -3121650569779268081L;

	private final static Calendar calendar = Calendar.getInstance();
	
	private final static NumberFormat numFormat = NumberFormat.getInstance();
	
	private String pattern;

	public JSONDateFormat(String pattern) {
		super.setCalendar(calendar);
		super.setNumberFormat(numFormat);
		this.pattern = pattern;
	}

	@Override
	public StringBuffer format(Date date, StringBuffer toAppendTo,
			FieldPosition fieldPosition) {
		toAppendTo.append(new DateTime(date.getTime()).toString(pattern));
		return toAppendTo;
	}

	@Override
	public Date parse(String source, ParsePosition pos) {
		return DateTimeFormat.forPattern(pattern).parseDateTime(source)
				.toDate();
	}

	@Override
	public Date parse(String source) throws ParseException {
		return parse(source,null);
	}
	
 
//	@Override
//	public Object clone() {
//		return super.clone();
//	}

}
