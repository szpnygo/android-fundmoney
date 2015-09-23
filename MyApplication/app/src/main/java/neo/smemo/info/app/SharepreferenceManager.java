package neo.smemo.info.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import neo.smemo.info.util.StringUtil;

public class SharepreferenceManager {
    /**
     * 模式
     */
    public static int __sdkLevel = Build.VERSION.SDK_INT;
    public static final int mode = ((__sdkLevel > 10) ? 4 : 0);

    public static String getSpString(Context context, String key) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences(Constant.MYPREFS, mode);
        return mySharedPreferences.getString(key, "");
    }

    public static String getSpString(Context context, String key, String defaut) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences(Constant.MYPREFS, mode);
        return mySharedPreferences.getString(key, defaut);
    }

    public static int getSpInt(Context context, String key) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences(Constant.MYPREFS, mode);
        return mySharedPreferences.getInt(key, 0);
    }

    public static int getSpInt(Context context, String key, int defaultNum) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences(Constant.MYPREFS, mode);
        return mySharedPreferences.getInt(key, defaultNum);
    }

    public static Boolean getSpBoolean(Context context, String key, boolean chioce) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences(Constant.MYPREFS, mode);
        return mySharedPreferences.getBoolean(key, chioce);

    }

    public static void saveSpString(Context context, String key, String value) {
        if (StringUtil.isEmpty(value) || value.equals("null")) value = "";
        SharedPreferences mySharedPreferences = context.getSharedPreferences(Constant.MYPREFS,
                mode);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void saveSpInt(Context context, String key, int value) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences(Constant.MYPREFS,
                mode);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static void saveSpBoolean(Context context, String key, Boolean value) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences(Constant.MYPREFS,
                mode);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static void remove(Context context, String key) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences(Constant.MYPREFS,
                mode);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }



}
