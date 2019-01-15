package com.heshan.framework.utils.jackson2;

import java.io.IOException;
import java.rmi.UnexpectedException;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;

/**
 * Jackson2Utils，jackson2.x中jackson-databind的实用工具类，参见
 * https://github.com/FasterXML/jackson-databind
 *
 * @author Erich
 *
 */
public abstract class JsonUtils {

    private JsonUtils() {

    }

    /**
     * 将给定的json字符串反序列化成java对象
     *
     * @param <T>
     * @param content
     *            给定的json字符串
     * @param targetType
     *            目标类型
     * @return
     */
    public static <T> T unmarshal(String content, Class<T> targetType) {

        if (StringUtils.isBlank(content)) {
            return null;
        }

        try {
            return DefaultMapperHolder.OBJECT_MAPPER.readValue(content, targetType);
        } catch (Exception e) {
            throw transJacksonException(e);
        }
    }
    
    public static <T> T unmarshal(byte[] content, Class<T> targetType) {
        if (content == null) {
            return null;
        }
        try {
            return DefaultMapperHolder.OBJECT_MAPPER.readValue(content, targetType);
        } catch (Exception e) {
            throw transJacksonException(e);
        }
    }

    /**
     * 将给定的json字符串反序列化成java对象，适合类型中包含泛型参数类型的情况
     *
     * @param <T>
     * @param content
     * @param targetType
     * @param parameterTypes
     * @return
     */
    public static <T> T unmarshal(String content, Class<T> targetType, Class<?>... parameterTypes) {
        if (StringUtils.isBlank(content)) {
            return null;
        }
        JavaType javaType = DefaultMapperHolder.OBJECT_MAPPER.getTypeFactory().constructParametrizedType(targetType,
            targetType, parameterTypes);
        try {
            return DefaultMapperHolder.OBJECT_MAPPER.readValue(content, javaType);
        } catch (Exception e) {
            throw transJacksonException(e);
        }
    }

    /**
     * 使用给定的json字符串，局部更新给定的对象
     *
     * @param <T>
     * @param content
     *            给定的json字符串
     * @param beanToUpdate
     *            给定的对象
     * @return
     */
    public static <T> T unmarshal(String content, T beanToUpdate) {
        try {
            return DefaultMapperHolder.OBJECT_MAPPER.readerForUpdating(beanToUpdate).readValue(content);
        } catch (Exception e) {
            throw transJacksonException(e);
        }
    }
    
    /**
     * 将给定的java对象序列化json字符串. 默认情况下，该Java对象上的所有属性都将被序列化。 但如果某个属性上标注了
     * {@link JsonIgnore}注解，则这个属性将不会被序列化。 如果某个属性上标注了{@link JsonInclude}
     * 注解，将定制该属性在null,empty等值的情况下是否被序列化。 属性在null,empty等值的情况下是否被序列化，还可以使用:
     * {@link JsonUtils#marshalNonDefault(Object)},
     * {@link JsonUtils#marshalNonEmpty(Object)},
     * {@link JsonUtils#marshalNonNull(Object)}等方法控制所有属性的这些行为。
     *
     * @param obj
     *            给定的java对象
     * @return 序列化后的JSON字符串
     */
    public static String marshal(Object obj) {
        try {
            return DefaultMapperHolder.OBJECT_MAPPER.writeValueAsString(obj);
        } catch (Exception e) {
            throw transJacksonException(e);
        }
    }

    /**
     * 将给定的java对象序列化json字符串，忽略给定对象的null值属性.
     *
     *
     * @param obj
     *            给定的java对象
     * @return
     */
    public static String marshalNonNull(Object obj) {
        try {
            return NonNullMapperHodler.OBJECT_MAPPER.writeValueAsString(obj);
        } catch (Exception e) {
            throw transJacksonException(e);
        }
    }

    /**
     * 将给定的java对象序列化json字符串，忽略给定对象的初始值未改变的属性
     *
     * @param obj
     *            给定的java对象
     * @return
     */
    public static String marshalNonDefault(Object obj) {
        try {
            return NonDefaultMapperHolder.OBJECT_MAPPER.writeValueAsString(obj);
        } catch (Exception e) {
            throw transJacksonException(e);
        }
    }

    /**
     * 将给定的java对象序列化json字符串，忽略给定对象的属性为Map或Collection时isEmpty方法为true的情况,
     * String或数组length为0的情况
     *
     * @param obj
     * @return
     */
    public static String marshalNonEmpty(Object obj) {
        try {
            return NonEmptyMapperHolder.OBJECT_MAPPER.writeValueAsString(obj);
        } catch (Exception e) {
            throw transJacksonException(e);
        }
    }

    /**
     * 将给定的对象使用给定的functionName序列化成JSONP格式
     *
     * @param functionName
     *            给定的functionName
     * @param obj
     *            给定的对象
     * @return
     */
    public static String marshalToJSONP(String functionName, Object obj) {
        JSONPObject jsonpObject = new JSONPObject(functionName, obj);
        return marshal(jsonpObject);
    }

    /**
     * 将Jacson API的checked异常转换成unchecked异常
     *
     * @param e
     * @return
     */
    private static UnexpectedException transJacksonException(Exception e) {
        if (e instanceof JsonParseException) {
            return new UnexpectedException("Json Parse Exception: " + e.getMessage(), e);
        }

        if (e instanceof JsonMappingException) {
            return new UnexpectedException("Json Mapping Exception: " + e.getMessage(), e);
        }

        if (e instanceof JsonProcessingException) {
            return new UnexpectedException("Json Mapping Exception: " + e.getMessage(), e);
        }

        if (e instanceof IOException) {
            return new UnexpectedException("IO Exception: " + e.getMessage(), e);
        }

        return new UnexpectedException("Unexpected exception thrown", e);
    }

    private static final class DefaultMapperHolder {
        protected static final ObjectMapper OBJECT_MAPPER = newObjectMapper(Include.ALWAYS);

        private DefaultMapperHolder() {
        }
    }

    private static final class NonNullMapperHodler {
        protected static final ObjectMapper OBJECT_MAPPER = newObjectMapper(Include.NON_NULL);

        private NonNullMapperHodler() {
        }
    }

    private static final class NonDefaultMapperHolder {
        protected static final ObjectMapper OBJECT_MAPPER = newObjectMapper(Include.NON_DEFAULT);

        private NonDefaultMapperHolder() {
        }
    }

    private static final class NonEmptyMapperHolder {
        protected static final ObjectMapper OBJECT_MAPPER = newObjectMapper(Include.NON_EMPTY);

        private NonEmptyMapperHolder() {
        }
    }

    private static ObjectMapper newObjectMapper(Include include) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(include);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, true);
        return mapper;
    }
}
