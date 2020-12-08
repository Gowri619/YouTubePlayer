package com.example.kotlinsample.di.utils;

import com.example.kotlinsample.di.exception.DateParsingException;
import com.example.kotlinsample.di.wrapper.ErrorWrapper;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import org.joda.time.DateTime;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class TypeAdapterUtils {
    public static TypeAdapter<Boolean> getBooleanAdapter() {
        return new TypeAdapter<Boolean>() {
            @Override
            public void write(JsonWriter out, Boolean value) throws IOException {
                if (out!=null){
                    out.value(value);
                }
            }

            @Override
            public Boolean read(JsonReader in) throws IOException {
                JsonToken token = in.peek();
                switch (token) {
                    case BOOLEAN:
                        return in.nextBoolean();
                    case NULL:
                        in.nextNull();
                        return null;
                    case NUMBER:
                        return in.nextInt() != 0;
                    case STRING:
                        return Boolean.parseBoolean(in.nextString());
                    default:
                        throw new IllegalStateException("Expected BOOLEAN or NUMBER but was " + token);
                }
            }
        };
    }

    public static TypeAdapter<DateTime> getDateTimeAdapter() {
        return new TypeAdapter<DateTime>() {
            @Override
            public void write(JsonWriter out, DateTime value) throws IOException {
                if (out != null) {
                    out.value(DateTimeUtil.toRequestString(value));
                }
            }

            @Override
            public DateTime read(JsonReader in) throws IOException {
                long dateValue = DateTime.now().getMillis();
                if (in.peek() == JsonToken.NUMBER) {
                    try {
                        return DateTimeUtil.toDateTimeFromMillis(dateValue = (in.nextLong() * 1000));
                    } catch (Exception e) {
                        throw new DateParsingException(dateValue, e);
                    }
                }

                throw new DateParsingException(dateValue, null);
            }
        };
    }

    public static TypeAdapter<ErrorWrapper> getErrorMapAdapter() {
        return new TypeAdapter<ErrorWrapper>() {
            @Override
            public void write(JsonWriter out, ErrorWrapper value) throws IOException {
                if (out != null) {
                    out.value(value.getMessage());
                }
            }

            @Override
            public ErrorWrapper read(JsonReader in) throws IOException {

                ErrorWrapper errorWrapper = new ErrorWrapper();
                JsonToken jsonToken = in.peek();

                if (jsonToken == JsonToken.BEGIN_OBJECT) {
                    in.beginObject();
                }

                if (in.peek() == JsonToken.NAME) {
                    in.nextName();
                }

                jsonToken = in.peek();

                if (jsonToken == JsonToken.STRING) {
                    try {
                        errorWrapper.setMessage(in.nextString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (jsonToken == JsonToken.BEGIN_ARRAY) {
                    try {
                        List<String> listOfError = new Gson().fromJson(in, new TypeToken<List<String>>() {
                        }.getType());
                        HashMap<String, List<String>> map = new HashMap<>();
                        map.put("error", listOfError);
                        errorWrapper.setMapErrors(map);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                return errorWrapper;
            }
        };
    }
}

