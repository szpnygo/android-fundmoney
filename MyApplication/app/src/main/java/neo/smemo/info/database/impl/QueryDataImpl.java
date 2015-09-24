package neo.smemo.info.database.impl;

import android.database.Cursor;

import java.util.ArrayList;

import neo.smemo.info.bean.FundBean;
import neo.smemo.info.database.BaseDataHelper;
import neo.smemo.info.database.ConstantDb;
import neo.smemo.info.database.DbController;
import neo.smemo.info.util.LogHelper;

/**
 *
 */
public class QueryDataImpl extends BaseDataHelper implements ConstantDb {

    /**
     * 根据ID查询
     *
     * @param name 基金名
     * @return 基金数据
     */
    public FundBean queryFundByFundid(String name) {
        Cursor cursor = null;
        try {
            cursor = DbController.getInstance(dbHelper).query(TABLE_NAME_FUND, null, "name", name, null, null, 1);
            if (cursor.getCount() > 0) {
                if (cursor.moveToNext()) {
                    FundBean fundBean = new FundBean();
                    DbController.cursorToObject(fundBean, cursor);
                    return fundBean;
                }
            }
        } catch (Exception e) {
            LogHelper.Log_E(TAG_Database, e.getMessage());
        }
        if (cursor != null)
            cursor.close();
        return null;
    }

    /**
     * 查询所有数据
     *
     * @return
     */
    public ArrayList<FundBean> queryFundDatas() {
        Cursor cursor = null;
        ArrayList<FundBean> arrayList = new ArrayList<>();
        try {
            cursor = DbController.getInstance(dbHelper).query(TABLE_NAME_FUND, null, null, null, null, null, -1);
            while (cursor.moveToNext()) {
                FundBean fundBean = new FundBean();
                DbController.cursorToObject(fundBean, cursor);
                arrayList.add(fundBean);
            }
        } catch (Exception e) {
            LogHelper.Log_E(TAG_Database, e.getMessage());
        }
        if (cursor != null)
            cursor.close();
        return arrayList;

    }

}
