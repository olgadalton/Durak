/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.HashMap;

/**
 *
 * @author Olga
 */
public class JSONUtil {
    
    public static String jsonEncode(Object obj) {
        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(obj);
        return json;
    }
    
    public static Object decodeJson(String jsonString) {
        Gson gson = new Gson();
        Type type = new TypeToken<HashMap<String, Object>>(){}.getType();
        Object result = gson.fromJson(jsonString, type);
        return result;
    }
    
}
