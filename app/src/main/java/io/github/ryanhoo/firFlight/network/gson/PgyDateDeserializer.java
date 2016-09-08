package io.github.ryanhoo.firFlight.network.gson;

import android.util.Log;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by GuDong on 8/29/16 21:33.
 * Contact with gudong.name@gmail.com.
 */
public class PgyDateDeserializer implements JsonDeserializer<Date> {
    private static final String KEY_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String dateString = json.getAsString();
        Log.i("dateString",dateString);
        SimpleDateFormat format = new SimpleDateFormat(KEY_FORMAT);
        try {
            return format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
