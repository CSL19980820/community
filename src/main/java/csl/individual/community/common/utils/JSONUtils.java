package csl.individual.community.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.JSONLibDataFormatSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import javafx.animation.KeyValue;

import java.util.List;
import java.util.Map;

/**
 * Created by 陈伟强 on 2017-7-26.
 */
public class JSONUtils {
    private static final SerializeConfig config;

    static {
        config = new SerializeConfig();
        config.put(java.util.Date.class, new JSONLibDataFormatSerializer()); // 使用和json-lib兼容的日期输出格式
        config.put(java.sql.Date.class, new JSONLibDataFormatSerializer()); // 使用和json-lib兼容的日期输出格式
    }

    private static final SerializerFeature[] features = {
            SerializerFeature.WriteMapNullValue, // 输出空置字段
            SerializerFeature.WriteNullListAsEmpty, // list字段如果为null，输出为[]，而不是null
            SerializerFeature.WriteNullNumberAsZero, // 数值字段如果为null，输出为0，而不是null
            SerializerFeature.WriteNullBooleanAsFalse, // Boolean字段如果为null，输出为false，而不是null
            SerializerFeature.WriteNullStringAsEmpty // 字符类型字段如果为null，输出为""，而不是null
    };

    public static String toJSONString(Object object) {
        return JSON.toJSONString(object, config, features);
    }

    public static String toJSONNoFeatures(Object object) {
        return JSON.toJSONString(object, config);
    }

    public static Object toBean(String text) {
        return JSON.parse(text);
    }

    public static <T> T toBean(String text, Class<T> clazz) {
        return JSON.parseObject(text, clazz);
    }

    // 转换为数组
    public static <T> Object[] toArray(String text) {
        return toArray(text, null);
    }

    // 转换为数组
    public static <T> Object[] toArray(String text, Class<T> clazz) {
        return JSON.parseArray(text, clazz).toArray();
    }

    // 转换为List
    public static <T> List<T> toList(String text, Class<T> clazz) {
        return JSON.parseArray(text, clazz);
    }

    /**
     * 将javabean转化为序列化的json字符串
     * @param keyvalue 键值
     * @return {Object}
     */
    public static Object beanToJson(KeyValue keyvalue) {
        return JSON.parse(JSON.toJSONString(keyvalue));
    }

    /**
     * 将string转化为序列化的json字符串
     * @param text string
     * @return {Object}
     */
    public static Object textToJson(String text) {
        return JSON.parse(text);
    }

    /**
     * json字符串转化为map
     * @param s string
     * @return {Map}
     */
    public static Map stringToCollect(String s) {
        return JSONObject.parseObject(s);
    }

    /**
     * 将map转化为string
     * @param m Map
     * @return {String}
     */
    public static String collectToString(Map m) {
        return JSONObject.toJSONString(m);
    }

    /**
     * 实体转对象
     * @param obj obj
     * @param clazz clazz
     * @param <T> T
     * @return T
     */
    public static <T> T toObject(Object obj,Class<T> clazz){
        return JSONObject.parseObject(JSON.toJSONString(obj),clazz);
    }

    /**
     * 实体转List
     * @param obj obj
     * @param clazz clazz
     * @param <T> G
     * @return List
     */
    public static <T> List<T> toArray(Object obj,Class<T> clazz){
        return JSONArray.parseArray(JSON.toJSONString(obj),clazz);
    }

    //List的深拷贝
    public static <T> List cloneList(List list,Class<T> clazz){
        return toArray(list,clazz);
    }

    //对象深拷贝
    public static <T> T cloneObj(Object obj,Class<T> clazz){
        return toObject(obj,clazz);
    }
}
