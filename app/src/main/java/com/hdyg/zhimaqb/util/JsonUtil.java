package com.hdyg.zhimaqb.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Gson解析Json工具类
 * Created by Administrator on 2017/5/4.
 *
 */

public class JsonUtil {
    /**
     * 泛型
     * @param jsonData json数据
     * @param type 实体类
     * @param <T> 实体类
     * @return result 对象
     */
    public static <T> T parseJsonWithGson(String jsonData, Class<T> type){
        T result = null;
        Gson gson = new Gson();
        result = gson.fromJson(jsonData,type);
        return result;
    }


//    [
//    {
//        "first": ["a","b","c"],
//        "ibeaconkey": "7FA707B8-BD07-B2F4-32BF-F51C6E7BEF80$10103$1",
//        "second": ["a","b","c"],
//        "third": ["a","b","c"],
//        "x": 65,
//        "y": 130
//    },
//    {
//        "first": ["a","b","c"],
//        "ibeaconkey": "7FA707B8-BD07-B2F4-32BF-F51C6E7BEF80$10102$1",
//        "second": ["a","b","c"],
//        "third": ["a","b","c"],
//        "x": 100,
//        "y": 180
//    }
//    ]
    /**
     * @param json
     * @param clazz
     * @return
     */
    public static <T> ArrayList<T> jsonToArrayList(String json, Class<T> clazz)
    {
        Type type = new TypeToken<ArrayList<JsonObject>>()
        {}.getType();
        ArrayList<JsonObject> jsonObjects = new Gson().fromJson(json, type);

        ArrayList<T> arrayList = new ArrayList<>();
        for (JsonObject jsonObject : jsonObjects)
        {
            arrayList.add(new Gson().fromJson(jsonObject, clazz));
        }
        return arrayList;
    }
}
