package neo.smemo.info.app;

import android.os.Environment;

/**
 * 常量
 * Created by suzhenpeng on 15/5/2.
 */
public interface Constant {

    String LOG_TAG = "neo.smemo.info";

    String MYPREFS = "neo.smemo.info";

    String TAG_Network = "neo_network";
    String StorageLocation_ROOT = Environment.getExternalStorageDirectory() + "/neo.smemo.info/";
    String StorageLocation_LOG = Environment.getExternalStorageDirectory() + "/neo.smemo.info/log/";
}
