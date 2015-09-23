/**
 * 项目名称：SmartCalendar2.3.0
 * 类名称：SystemUtil
 * 类描述： 系统工具类
 * 创建人：suzhenpeng
 * 创建时间：2013-9-8 下午6:47:31
 * 修改人：suzhenpeng
 * 修改时间：2013-9-8 下午6:47:31
 * 修改备注：
 *
 * @version
 */
package neo.smemo.info.util.system;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SystemUtil {
    /**
     * @param @param  context
     * @param @return
     * @return int
     * @throws
     * @Title: getPhoneWidth
     * @Description: 获取屏幕宽度
     */
    @SuppressWarnings("deprecation")
    public static int getPhoneWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);

        return wm.getDefaultDisplay().getWidth();
    }

    /**
     * @param @param  context
     * @param @return
     * @return int
     * @throws
     * @Title: getPhoneHeight
     * @Description: 获取屏幕高度
     */
    @SuppressWarnings("deprecation")
    public static int getPhoneHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);

        return wm.getDefaultDisplay().getHeight();
    }

    /**
     * @param @param context
     * @param @param str
     * @return void
     * @throws
     * @Title: writeToData
     * @Description:写入数据到缓存
     */
    public static void writeToData(Context context, String str, String fileNmae) {
        File file = new File(context.getCacheDir(), fileNmae);
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            byte[] content = str.getBytes();
            outputStream.write(content);
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param @param context
     * @param @param msg
     * @return void
     * @throws
     * @Title: toastMessage
     * @Description:信息提示
     */
    public static void toastMessage(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void toastMessage(Context context, int res) {
        String msg = context.getResources().getString(res);
        toastMessage(context, msg);
    }

    /**
     * @param @param handler
     * @param @param msg
     * @return void
     * @throws
     * @Title: sendMessage
     * @Description:发送消息
     */
    public static void sendMessage(Handler handler, int msg) {
        if (handler == null)
            return;
        Message message = new Message();
        message.what = msg;
        handler.sendMessage(message);
    }

    public static void sendMessage(Handler handler, int msg, String data) {
        if (handler == null)
            return;
        Bundle bundle = new Bundle();
        bundle.putString("data", data);
        Message message = new Message();
        message.what = msg;
        message.setData(bundle);
        handler.sendMessage(message);
    }

    /**
     * @param @param handler
     * @param @param msg
     * @return void
     * @throws
     * @Title: sendMessage
     * @Description:发送消息
     */
    public static void sendMessage(Handler handler, int msg, Bundle bundle) {
        if (handler == null || bundle == null)
            return;
        Message message = new Message();
        message.what = msg;
        message.setData(bundle);
        handler.sendMessage(message);
    }

    /**
     * @return boolean
     * @throws
     * @Title: existSDCard
     * @Description:判断SDCARD是否存在
     */
    public static boolean existSDCard() {
        return android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取配置文件中的versioncode
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        String versionname = "";// 版本号
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo("com.ztgame.wangzheguilai", 0);
            versionname = pi.versionName;// 获取在AndroidManifest.xml中配置的版本号
        } catch (PackageManager.NameNotFoundException e) {
            versionname = "1.0";
        }

        return versionname;

    }

    /**
     * 获取配置文件中的versioncode
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        int versionCode = 0;// 版本号
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionCode = pi.versionCode;// 获取在AndroidManifest.xml中配置的版本号
        } catch (PackageManager.NameNotFoundException e) {
            versionCode = 1;
        }

        return versionCode;

    }

    /**
     * 获取AndroidManifest中的MetaData属性
     *
     * @param context
     * @param metaName
     * @return
     */
    public static String getAndroidManifestMetaData(Context context,
                                                    String metaName) {
        ApplicationInfo appInfo;
        try {
            appInfo = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            return appInfo.metaData.getString(metaName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        } catch (NullPointerException e) {
            return "";
        }
    }

    /**
     * 发送广播
     *
     * @param action 广播名
     * @param extras 数据
     */
    public static void sendBroadcaset(Context context, String action,
                                      Bundle extras) {
        Intent intent = new Intent(action);
        if (null != extras)
            intent.putExtras(extras);
        context.sendBroadcast(intent);
    }

    public static void sendMessage(Context context, String phone, String data) {
        Uri uri = Uri.parse("smsto:" + phone);
        Intent sendIntent = new Intent(Intent.ACTION_VIEW, uri);
        sendIntent.putExtra("sms_body", data);
        context.startActivity(sendIntent);
    }

    public static void closeKeyMap(Activity activity) {
        View view = activity.getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
