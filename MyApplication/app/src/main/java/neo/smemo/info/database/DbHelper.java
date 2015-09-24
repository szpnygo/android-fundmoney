package neo.smemo.info.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import neo.smemo.info.app.AppApplication;

/**
 * 数据库创建升级
 * Created by suzhenpeng on 2015/5/20.
 */
public class DbHelper extends SQLiteOpenHelper implements ConstantDb {

    private final static int DATEBASE_VERSION = 1;
    public static DbHelper dbHelperInstance = null;

    public static DbHelper getInstance() {
        if (null == dbHelperInstance) {
            if (AppApplication.getContext() == null)
                throw new RuntimeException("Application not initialized");
            dbHelperInstance = new DbHelper(AppApplication.getContext());
        }
        return dbHelperInstance;
    }

    private DbHelper(Context context) {
        super(context, Database_Name, null, DATEBASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_FUND_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
