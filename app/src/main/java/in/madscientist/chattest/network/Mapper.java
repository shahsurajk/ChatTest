package in.madscientist.chattest.network;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;

/**
 * Created by frapp on 11/2/17.
 */

public final class Mapper {
    private static ObjectMapper MAPPER;

    public static ObjectMapper get() {
        if (MAPPER == null) {
            MAPPER = new ObjectMapper();

            // This is useful for me in case I add new object properties on the server side which are not yet available on the client.
            //MAPPER.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
            MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            MAPPER.configure(SerializationFeature.INDENT_OUTPUT, true);
            MAPPER.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
            MAPPER.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        }


        return MAPPER;
    }

    public static String string(Object data) {
        try {
            return get().writeValueAsString(data);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T objectOrThrow(String data, Class<T> type) throws IOException {
        return get().readValue(data, type);
    }

    public static <T> T object(String data, Class<T> type) {
        try {
            return objectOrThrow(data, type);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
