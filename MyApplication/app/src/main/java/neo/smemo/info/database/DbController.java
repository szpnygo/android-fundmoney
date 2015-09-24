package neo.smemo.info.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import java.lang.reflect.Field;

import neo.smemo.info.app.Constant;
import neo.smemo.info.util.LogHelper;
import neo.smemo.info.util.StringUtil;

/**
 * 数据操作类
 * Created by suzhenpeng on 2015/5/20.
 */
public class DbController implements ConstantDb {

    public static DbController instance;
    public DbHelper dbHelper;
    public static SQLiteDatabase db;

    public DbController(DbHelper dbHelper) {
        this.dbHelper = dbHelper;
        initDb();
    }

    private void initDb() {
        try {
            db = dbHelper.getWritableDatabase();
        } catch (Exception e) {
            db = dbHelper.getReadableDatabase();
        }
    }

    public static DbController getInstance(DbHelper dbHelper) {
        if (instance == null)
            instance = new DbController(dbHelper);
        return instance;
    }

    public Cursor rawQuery(String sql) {
        initDb();
        return db.rawQuery(sql, null);
    }

    public void execSQL(String sql) {
        initDb();
        db.execSQL(sql);
    }

    /**
     * 删除数据库
     *
     * @param context
     * @return
     */
    public boolean deleteDatabase(Context context) {
        return context.deleteDatabase(Database_Name);
    }

    /**
     * 插入数据库
     *
     * @param tableName
     * @param object
     * @return
     */
    public long insertData(String tableName, @NonNull Object object) {
        initDb();
        LogHelper.Log_I(Constant.TAG_Database, "insert sql=[" + object.getClass().getName() + "]");
        return db.insert(tableName, null, toContentValues(object));
    }

    private static final String QUERY_SQL_WITHOUTORDER = "select %s from %s";
    private static final String QUERY_SQL = "select %s from %s ORDER BY %s";
    private static final String QUERY_STRING_ID_SQL = "select %s from %s where %s='%s' ORDER BY %s";
    private static final String QUERY_STRING_ID_SQL_WITHOUTORDER = "select %s from %s where %s='%s'";

    /**
     * 查询数据
     *
     * @param tablename
     * @param columns
     * @param idname
     * @param id
     * @param sort
     * @return
     */
    public Cursor query(String tablename, String[] columns, String idname, String id, String sort, String sortkey, int limit) {
        String column = "*";
        if (columns != null && columns.length > 0) {
            StringBuilder sb = new StringBuilder();
            for (String tmp : columns)
                sb.append(tmp).append(",");
            sb.deleteCharAt(sb.length() - 1);
            column = sb.toString();
        }
        String sql = null;
        if (StringUtil.isEmpty(id) || StringUtil.isEmpty(idname)) {
            if (StringUtil.isEmpty(sort) || DB_SORT_NULL.equals(sort)) {
                sql = String.format(QUERY_SQL_WITHOUTORDER, column, tablename);
            } else {
                sql = String.format(QUERY_SQL, column, tablename, sortkey) + " " + sort;
            }
        } else {
            if (StringUtil.isEmpty(sort) || DB_SORT_NULL.equals(sort)) {
                sql = String.format(QUERY_STRING_ID_SQL_WITHOUTORDER, column, tablename, idname, id);
            } else {
                sql = String.format(QUERY_STRING_ID_SQL, column, tablename, idname, id, sortkey) + " " + sort;
            }
        }
        if (limit > 0) {
            sql = sql + " limit " + limit;
        }
        LogHelper.Log_I(Constant.TAG_Database, "query sql=[" + sql + "]");
        initDb();
        return db.rawQuery(sql, null);
    }

    private static final String DELETE_SQL = "delete from %s";
    private static final String DELETE_SQL_BY_STR = "delete from %s  where %s='%s'";

    /**
     * 删除数据
     *
     * @param tablename
     * @param idname
     * @param id
     */
    public void delete(String tablename, @NonNull String idname, @NonNull String id) {
        String sql = "";
        if (StringUtil.isEmpty(idname) || StringUtil.isEmpty(id)) {
            sql = String.format(DELETE_SQL, tablename);
        } else {
            sql = String.format(DELETE_SQL_BY_STR, tablename, idname, id);
        }
        LogHelper.Log_I(Constant.TAG_Database, "query sql=[" + sql + "]");
        initDb();
        db.execSQL(sql);
    }

    private static final String UPDATA_SQL = "update %s set %s='%s' where %s='%s'";

    /**
     * 更新数据
     *
     * @param tablename
     * @param idname
     * @param value
     */
    public void update(String tablename, @NonNull String idname, @NonNull String id, @NonNull String key, @NonNull String value) {
        String sql = "";
        if (StringUtil.isEmpty(idname) || StringUtil.isEmpty(value))
            return;
        sql = String.format(UPDATA_SQL, tablename, key, value, idname, id);
        LogHelper.Log_I(Constant.TAG_Database, "update sql=[" + sql + "]");
        initDb();
        db.execSQL(sql);
    }

    public int update(String tablename, @NonNull String idname, @NonNull String id, Object object) {
        ContentValues contentValues = toContentValues(object);
        initDb();
        return db.update(tablename, contentValues, (idname + "='" + id + "'"), null);
    }

    /**
     * object转换ContentValues
     *
     * @param object
     * @return
     */
    public static ContentValues toContentValues(@NonNull Object object) {
        if (object == null)
            return new ContentValues();
        if (object instanceof ContentValues)
            return (ContentValues) object;
        ContentValues contentValues = new ContentValues();
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            AnnotationDatabase annotationDatabase = field.getAnnotation(AnnotationDatabase.class);
            AnnotationDbUnUsed annotationDbUnUsed = field.getAnnotation(AnnotationDbUnUsed.class);
            try {
                if (annotationDbUnUsed == null) {
                    String key;
                    if (annotationDatabase != null) {
                        key = annotationDatabase.value();
                    } else {
                        key = field.getName();
                    }

                    String fileTypeName = field.getType().getName();
                    if (fileTypeName.equals("java.lang.String")) {
                        contentValues.put(key, String.valueOf(field.get(object)));
                    } else if (fileTypeName.equals("java.lang.Boolean")) {
                        contentValues.put(key, field.getBoolean(object));
                    } else if (fileTypeName.equals("int")) {
                        contentValues.put(key, field.getInt(object));
                    } else if (fileTypeName.equals("long")) {
                        contentValues.put(key, field.getLong(object));
                    } else if (fileTypeName.equals("float")) {
                        contentValues.put(key, field.getFloat(object));
                    } else if (fileTypeName.equals("double")) {
                        contentValues.put(key, field.getDouble(object));
                    } else {
                        contentValues.put(key, String.valueOf(field.get(object)));
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
        return contentValues;
    }

    /**
     * Cursor转换Object
     *
     * @param object
     * @param cursor
     * @return
     */
    public static Object cursorToObject(@NonNull Object object, Cursor cursor) throws IllegalAccessException {
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            AnnotationDbUnUsed annotationDbUnUsed = field.getAnnotation(AnnotationDbUnUsed.class);
            AnnotationDatabase annotationDatabase = field.getAnnotation(AnnotationDatabase.class);

            if (annotationDbUnUsed == null || annotationDatabase != null) {
                String key;
                if (annotationDatabase != null) {
                    key = annotationDatabase.value();
                } else {
                    key = field.getName();
                }
                String fileTypeName = field.getType().getName();
                int dbIndex = cursor.getColumnIndex(key);
                if (fileTypeName.equals("java.lang.String")) {
                    field.set(object, cursor.getString(dbIndex));
                } else if (fileTypeName.equals("int")) {
                    field.set(object, cursor.getInt(dbIndex));
                } else if (fileTypeName.equals("long")) {
                    field.set(object, cursor.getLong(dbIndex));
                } else if (fileTypeName.equals("float")) {
                    field.set(object, cursor.getFloat(dbIndex));
                } else if (fileTypeName.equals("double")) {
                    field.set(object, cursor.getDouble(dbIndex));
                }
            }
        }
        return object;
    }
}
