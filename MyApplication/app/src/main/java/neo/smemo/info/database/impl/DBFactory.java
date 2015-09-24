package neo.smemo.info.database.impl;

import neo.smemo.info.database.ConstantDb;

/**
 * 数据操作管理
 */
public class DBFactory implements ConstantDb {

    private static InsertDataImpl inset;
    private static QueryDataImpl query;
    private static DeleteDataImpl delete;
    private static UpdateDataImpl update;

    public static class SingletonInsert {
        private static InsertDataImpl insertDataInterf = new InsertDataImpl();
    }

    public static class SingletonQuery {
        private static QueryDataImpl queryDataInterf = new QueryDataImpl();
    }

    public static class SingletonDelete {
        private static DeleteDataImpl deleteDataInterf = new DeleteDataImpl();
    }

    public static class SingletonUpdate {
        private static UpdateDataImpl updateDataInterf = new UpdateDataImpl();
    }

    public synchronized static InsertDataImpl getInsertDatabaseImpl() {
        if (inset == null)
            inset = SingletonInsert.insertDataInterf;
        return inset;
    }

    public synchronized static QueryDataImpl getQueryDataImpl() {
        if (query == null)
            query = SingletonQuery.queryDataInterf;
        return query;
    }

    public synchronized static DeleteDataImpl getDeleteDataImpl() {
        if (delete == null)
            delete = SingletonDelete.deleteDataInterf;
        return delete;
    }

    public synchronized static UpdateDataImpl getUpdateDataInterf() {
        if (update == null)
            update = SingletonUpdate.updateDataInterf;
        return update;
    }
}
