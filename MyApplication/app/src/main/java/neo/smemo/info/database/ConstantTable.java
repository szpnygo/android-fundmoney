package neo.smemo.info.database;


import neo.smemo.info.app.Constant;

/**
 * 数据库SQL
 * Created by suzhenpeng on 2015/5/20.
 */
public interface ConstantTable extends Constant {

    String TABLE_NAME_FUND = "fundata";

    String SQL_CREATE_FUND_TABLE = "create table[" + TABLE_NAME_FUND + "](id integer primary key autoincrement," +
            "netid text," +
            "fund_time text," +
            "data_time text," +
            "company text," +
            "name text," +
            "fundid text," +
            "img text," +
            "fund_title text," +
            "fund_type text," +
            "fund_manager text," +
            "fund_money text," +
            "fund_maketime text," +
            "fund_director text," +
            "fund_director_info text," +
            "fund_profit text," +
            "fund_p_seven text," +
            "fund_p_fourteen text," +
            "fund_p_twenty text," +
            "fund_p_month text," +
            "fund_p_year text," +
            "date text)";

}
