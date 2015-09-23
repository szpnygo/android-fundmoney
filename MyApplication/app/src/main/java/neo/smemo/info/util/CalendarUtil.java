package neo.smemo.info.util;

import java.util.ArrayList;

/**
 * 
 * 日历工具
 * 
 * @author suzhenpeng
 * 
 */
public class CalendarUtil {
	/** 年份 */
	public ArrayList<String> years = null;
	/** 月 */
	public ArrayList<String> months = null;
	/** 十二小时 */
	public ArrayList<String> hour12 = null;
	/** 分钟 */
	public ArrayList<String> minute = null;
	/** 二十四小时 */
	public ArrayList<String> hour24 = null;
	/** 单例 */
	public static CalendarUtil model;
	/** 每月的天数 */
	public static final int[] MONTH_DAY = { 31, 28, 31, 30, 31, 30, 31, 31, 30,
			31, 30, 31 };
	/** 十二小时制 */
	public static final int HOUR = 12;
	/** 二十四小时制 */
	public static final int HOUR_OF_DAY = 24;

	private CalendarUtil() {
	}

	/**
	 * 获取单例
	 * 
	 * @return
	 */
	public static CalendarUtil getSingleton() {
		if (null == model) {
			model = new CalendarUtil();
		}
		return model;
	}

	/**
	 * 获取所有的年份
	 * 
	 * @return
	 */
	public ArrayList<String> getYears() {
		int curyear=Integer.valueOf(TimeUtil.getFormatTime(String.valueOf(System.currentTimeMillis()), "yyyy"));
		if (years == null) {
			years = new ArrayList<String>();
			years.clear();
			for (int i = 1970; i < curyear+1; i++) {
				years.add(String.valueOf(i));
			}
		}
		return years;
	}

	/**
	 * 获取所有的月份
	 * 
	 * @return
	 */
	public ArrayList<String> getMonths() {
		if (months == null) {
			months = new ArrayList<String>();
			months.clear();
			for (int i = 1; i < 13; i++) {
				if (i < 10) {
					months.add("0" + String.valueOf(i));
				} else {
					months.add(String.valueOf(i));
				}
			}
		}
		return months;
	}

	/**
	 * 获取天数
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public ArrayList<String> getDays(int year, int month) {
		int num = MONTH_DAY[month];
		if (isLeapYear(year) && month == 1)
			num += 1;
		ArrayList<String> days = new ArrayList<String>();
		for (int i = 1; i <= num; i++) {
			if (i < 10) {
				days.add("0" + String.valueOf(i));
			} else {
				days.add(String.valueOf(i));
			}
		}
		return days;
	}

	/**
	 * 是否是闰年
	 * 
	 * @param year
	 * @return
	 */
	public boolean isLeapYear(int year) {
		return (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
	}

	/**
	 * 返回小时
	 * 
	 * @param TYPE
	 *            HOUR 12小时，HOUR_OF_DAY 24小时
	 * @return
	 */
	public ArrayList<String> getHours(int TYPE) {
		if (TYPE == HOUR) {
			if (hour12 == null) {
				hour12=new ArrayList<String>();
				for (int i = 1; i <= HOUR; i++) {
					if (i < 10) {
						hour12.add("0" + String.valueOf(i));
					} else {
						hour12.add(String.valueOf(i));
					}
				}
			}
			return hour12;
		} else if (TYPE == HOUR_OF_DAY) {
			if (hour24 == null) {
				hour24=new ArrayList<String>();
				for (int i = 0; i < HOUR_OF_DAY; i++) {
					if (i < 10) {
						hour24.add("0" + String.valueOf(i));
					} else {
						hour24.add(String.valueOf(i));
					}
				}
			}
			return hour24;
		}
		return null;
	}

	/**
	 * 返回分钟
	 * 
	 * @return
	 */
	public ArrayList<String> getMinute() {
		if (minute == null) {
			minute=new ArrayList<String>();
			for (int i = 0; i < 60; i++) {
				if (i < 10) {
					minute.add("0" + String.valueOf(i));
				} else {
					minute.add(String.valueOf(i));
				}
			}
		}
		return minute;
	}

}
