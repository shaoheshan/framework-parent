package com.heshan.framework.utils.jackson2;

import java.io.IOException;
import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class DateSerializers {

    private DateSerializers() {
    }

    public static final class ISO8601Long extends JsonSerializer<Date> {
        @Override
        public void serialize(Date value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
            DateSerializers.serialize(jgen, value, "yyyy-MM-dd HH:mm:ss");
        }

    }

    public static final class ISO8601Short extends JsonSerializer<Date> {

        @Override
        public void serialize(Date value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
            DateSerializers.serialize(jgen, value, "yyyy-MM-dd");
        }

    }

    private static void serialize(JsonGenerator jgen, Date value, String format) throws IOException {
        if (value == null) {
            jgen.writeNull();
        } else {
            jgen.writeString(DateFormatUtils.format(value, format));
        }
    }
}

