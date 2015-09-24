package neo.smemo.info.database;

/**
 * Created by suzhenpeng on 2015/9/24.
 */
public class BaseDataHelper {
    protected DbHelper dbHelper;

    public BaseDataHelper() {
        dbHelper = DbHelper.getInstance();
    }
}
