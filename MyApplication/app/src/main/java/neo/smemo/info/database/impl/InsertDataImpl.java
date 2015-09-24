package neo.smemo.info.database.impl;


import neo.smemo.info.bean.FundBean;
import neo.smemo.info.database.BaseDataHelper;
import neo.smemo.info.database.ConstantDb;
import neo.smemo.info.database.DbController;

public class InsertDataImpl extends BaseDataHelper implements ConstantDb {

    public long insertFundData(FundBean fundBean) {
        if (DBFactory.getQueryDataImpl().queryFundByFundid(fundBean.name) == null) {
            return DbController.getInstance(dbHelper).insertData(TABLE_NAME_FUND, fundBean);
        }
        return DbController.getInstance(dbHelper).update(TABLE_NAME_FUND, "name", fundBean.name, fundBean);
    }


}
