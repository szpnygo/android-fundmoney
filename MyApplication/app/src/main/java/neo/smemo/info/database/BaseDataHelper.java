package neo.smemo.info.database;

public class BaseDataHelper {
    protected DbHelper dbHelper;

    public BaseDataHelper() {
        dbHelper = DbHelper.getInstance();
    }
}
