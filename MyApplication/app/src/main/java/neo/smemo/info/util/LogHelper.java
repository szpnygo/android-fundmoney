package neo.smemo.info.util;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import neo.smemo.info.app.AppConfig;
import neo.smemo.info.app.Constant;

public class LogHelper {

	public static boolean NO_DEBUG = AppConfig.NO_DEBUG;

	public static void Log_D(Context context, String log) {
		if (NO_DEBUG)
			return;
		Log.d(Constant.LOG_TAG + "-"+ TimeUtil.getTime() +" : " + context.getClass().getSimpleName(), log);
	}

	public static void Log_V(Context context, String log) {
		if (NO_DEBUG)
			return;
		Log.v(Constant.LOG_TAG + "-"+ TimeUtil.getTime() +" : " + context.getClass().getSimpleName(), log);
	}

	public static void Log_W(Context context, String log) {
		if (NO_DEBUG)
			return;
		Log.w(Constant.LOG_TAG + "-"+ TimeUtil.getTime() +" : " + context.getClass().getSimpleName(), log);
	}

	public static void Log_E(Context context, String log) {
		if (NO_DEBUG)
			return;
		Log.e(Constant.LOG_TAG + "-"+ TimeUtil.getTime() +" : "+ TimeUtil.getTime() +" : " + context.getClass().getSimpleName(), log);
	}

	public static void Log_I(Context context, String log) {
		if (NO_DEBUG)
			return;
		Log.i(Constant.LOG_TAG + "-"+ TimeUtil.getTime() +" : " + context.getClass().getSimpleName(), log);
	}

	public static void Log_D(String tag, String log) {
		if (NO_DEBUG)
			return;
		Log.d(Constant.LOG_TAG + "-"+ TimeUtil.getTime() +" : " + tag, log);
	}

	public static void Log_V(String tag, String log) {
		if (NO_DEBUG)
			return;
		Log.v(Constant.LOG_TAG + "-"+ TimeUtil.getTime() +" : " + tag, log);
	}

	public static void Log_W(String tag, String log) {
		if (NO_DEBUG)
			return;
		Log.w(Constant.LOG_TAG +"-"+ TimeUtil.getTime() +" : " + tag, log);
	}

	public static void Log_E(String tag, String log) {
		if (NO_DEBUG)
			return;
		Log.e(Constant.LOG_TAG + "-"+ TimeUtil.getTime() +" : " + tag, log);
		save(tag+"=="+log,"zmbox.log");
	}

	public static void Log_I(String tag, String log) {
		if (NO_DEBUG)
			return;
		Log.i(Constant.LOG_TAG + "-"+ TimeUtil.getTime() +" : " + tag, log);
	}

	/**
	 * @param content
	 * @param name
	 */
	public static void save(final String content,final String name) {
		if(NO_DEBUG)
			return;
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				String data="";
				if(!StringUtil.isEmpty(content))
					data=content;
				String log=TimeUtil.getTime() +" : "+data +"\n";
				File file = new File(Constant.StorageLocation_LOG);
				if (!file.exists())
					file.mkdirs();
				File logFile=new File(file, name);
				try {
					FileWriter writer = new FileWriter(logFile, true);
					writer.write(log);
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

}
