package neo.smemo.info.util;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * gson工具
 * Created by suzhenpeng on 2015/7/27.
 */
public class JsonUtil {

    public static JsonUtil jsonUtil;
    public Gson gson;

    public static JsonUtil getInstance() {
        if (jsonUtil == null)
            jsonUtil = new JsonUtil();
        return jsonUtil;
    }

    private JsonUtil() {
        gson = new Gson();
    }

    public <T> T fromJson(JSONObject object, Class<T> classOf) {
        try {
            Gson gson = new Gson();
            String data = object.getString("data");
            return gson.fromJson(data, classOf);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {

        }
        return null;
    }

    public <T> ArrayList<T> fromJsonArray(JSONObject object, Class<T> classOf) {
        try {
            Gson gson = new Gson();
            ArrayList<T> list = new ArrayList<>();
            JSONArray array = object.getJSONArray("data");
            for (int i = 0; i < array.length(); i++) {
                list.add(gson.fromJson(array.getString(i), classOf));
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
        }
        return null;
    }

}
