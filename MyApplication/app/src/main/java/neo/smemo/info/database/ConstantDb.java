package neo.smemo.info.database;

/**
 * 数据库常量
 * Created by suzhenpeng on 2015/5/20.
 */
public interface ConstantDb extends ConstantTable{

    String Database_Name = "neosu.sqlite3";
    String DB_SORT_NULL = "NULL"; //默认
    String DB_SORT_DESC = "DESC"; //倒序
    String DB_SORT_ASC = "ASC";  //正序排序

}
