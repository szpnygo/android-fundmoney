package neo.smemo.info.app;

/**
 * Created by suzhenpeng on 2015/5/20.
 */
public interface ConstantApi extends Constant {

    String API_HEADER="https://api.smemo.info/fund.php";

    String API_FUND_LIST=API_HEADER+"/index/getFundList";
}
