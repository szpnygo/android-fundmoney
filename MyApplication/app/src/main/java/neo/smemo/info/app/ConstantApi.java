package neo.smemo.info.app;

/**
 * 数据接口参数
 * Created by suzhenpeng on 2015/5/20.
 */
public interface ConstantApi extends Constant {

    String API_HEADER="https://api.smemo.info/fund.php";

    //获取基金列表
    String API_FUND_LIST=API_HEADER+"/index/getFundList";
    //获取基金历史
    String API_FUND_HISTORY=API_HEADER+"/index/getFundHistory";
}
