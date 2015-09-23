package neo.smemo.info.bean;

/**
 * Created by suzhenpeng on 2015/9/23.
 */
public class FundBean {

    public String id;
    public String fund_time;
    public String data_time;
    public String company;
    public String name;
    public String fundid;
    public String img;
    public String fund_title;
    public String fund_type;
    public String fund_manager;
    public String fund_money;
    public String fund_maketime;
    public String fund_director;
    public String fund_director_info;
    public String fund_profit;
    public String fund_p_seven;
    public String fund_p_fourteen;
    public String fund_p_twenty;
    public String fund_p_month;
    public String fund_p_year;
    public String date;

    @Override
    public String toString() {
        return "FundBean{" +
                "id='" + id + '\'' +
                ", fund_time='" + fund_time + '\'' +
                ", data_time='" + data_time + '\'' +
                ", company='" + company + '\'' +
                ", name='" + name + '\'' +
                ", fundid='" + fundid + '\'' +
                ", img='" + img + '\'' +
                ", fund_title='" + fund_title + '\'' +
                ", fund_type='" + fund_type + '\'' +
                ", fund_manager='" + fund_manager + '\'' +
                ", fund_money='" + fund_money + '\'' +
                ", fund_maketime='" + fund_maketime + '\'' +
                ", fund_director='" + fund_director + '\'' +
                ", fund_director_info='" + fund_director_info + '\'' +
                ", fund_profit='" + fund_profit + '\'' +
                ", fund_p_seven='" + fund_p_seven + '\'' +
                ", fund_p_fourteen='" + fund_p_fourteen + '\'' +
                ", fund_p_twenty='" + fund_p_twenty + '\'' +
                ", fund_p_month='" + fund_p_month + '\'' +
                ", fund_p_year='" + fund_p_year + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
