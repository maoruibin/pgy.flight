package com.moji.daypack.network.gson;

import android.util.Log;

import com.moji.daypack.data.model.IMessageContent;
import com.moji.daypack.data.model.impl.ReleaseMessageContent;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.moji.daypack.data.model.impl.SystemMessageContent;

import java.lang.reflect.Type;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 3/19/16
 * Time: 11:53 PM
 * Desc: MessageContentDeserializer
 */
public class MessageContentDeserializer implements JsonDeserializer<IMessageContent> {

    private static final String TAG = "MessageDeserializer";

    @Override
    public IMessageContent deserialize(JsonElement jsonElement, Type type,
                                       JsonDeserializationContext gson) throws JsonParseException {
        try {
            if (jsonElement.getAsJsonObject().has("link")) {
                // System
                return gson.deserialize(jsonElement, SystemMessageContent.class);
            } else if (jsonElement.getAsJsonObject().has("release")) {
                // Release
                return gson.deserialize(jsonElement, ReleaseMessageContent.class);
            }
        } catch (Exception e) {
            Log.e(TAG, "Deserialize failed " + jsonElement, e);
        }
        return null;
    }

}
