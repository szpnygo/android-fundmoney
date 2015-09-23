/**   
 *    
 * 项目名称：SmartCalendar2.3.0   
 * 类名称：TimeUtil   
 * 类描述：  时间工具类
 * 创建人：suzhenpeng  
 * 创建时间：2013-9-29 下午9:47:03   
 * 修改人：suzhenpeng 
 * 修改时间：2013-9-29 下午9:47:03   
 * 修改备注：   
 * @version    
 *    
 */
package neo.smemo.info.util;

import android.annotation.SuppressLint;
import android.net.ParseException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeUtil {
	/**
	 * @Title: workDays
	 * @Description:获取时间计算
	 * @param @param _setDate
	 * @param @return
	 * @return String
	 * @throws
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getWorkDays(Date _setDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		String setDateString = sdf.format(_setDate);

		Date today = new Date();
		long days = ((_setDate.getTime() / (24 * 60 * 60 * 1000)) - (today
				.getTime() / (24 * 60 * 60 * 1000)));
		String s = "";
		if (days < 0) {
			s = "已过";
			days = days * (-1);
		} else if (days == 0) {
			s = "";
		} else {
			s = "还有";
		}

		String getTiemString = "距离" + setDateString + s + days + "天";
		return getTiemString;
	}

	/**
	 * 从格式化的字符串获取时间
	 * 
	 * @param time
	 * @param format
	 * @return
	 */
	public static String formatToTime(String time, String format) {
		Date date = null;
		SimpleDateFormat formatTime = new SimpleDateFormat(format, Locale.CHINA);
		try {
			try {
				date = formatTime.parse(time);
			} catch (java.text.ParseException e) {
				e.printStackTrace();
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return String.valueOf(date.getTime());
	}

	/**
	 * 返回格式化时间
	 * 
	 * @return
	 */
	public static String getFormatTime(String time) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",
				Locale.CHINA);
		return dateFormat.format(Long.valueOf(time));
	}

	/**
	 * 返回指定格式时间
	 * 
	 * @param time
	 * @param format
	 * @return
	 */
	public static String getFormatTime(String time, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.CHINA);
		return dateFormat.format(Long.valueOf(time));
	}
	
	public static String getTime(){
		Date date=new Date();
		long time=date.getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.CHINA);
		return dateFormat.format(time);
	}

}
