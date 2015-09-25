package neo.smemo.info.model;

import java.util.Comparator;

import neo.smemo.info.bean.FundBean;

public class FundComparator implements Comparator<FundBean> {

    private int type;

    public static int SORT_BIG = 1;
    public static int SORT_SMALL = -1;

    public FundComparator(int type) {
        this.type = type;
    }

    @Override
    public int compare(FundBean lhs, FundBean rhs) {
        if (Float.valueOf(lhs.fund_profit) > Float.valueOf(rhs.fund_profit)) {
            return type == SORT_BIG ? 1 : -1;
        } else if (Float.valueOf(lhs.fund_profit) < Float.valueOf(rhs.fund_profit)) {
            return type == SORT_BIG ? -1 : 1;
        }
        return 0;
    }
}
