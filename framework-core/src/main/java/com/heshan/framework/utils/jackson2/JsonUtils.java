package com.heshan.framework.utils.jackson2;

import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.heshan.framework.utils.date.JSONDateFormat;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.databind.ser.std.NullSerializer;
/**
 * @description: jsonUtils 工具类
 * @version Ver 1.0
 */
public class JsonUtils {

	public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

	private static final Logger log = Logger.getLogger(JsonUtils.class);

	static boolean isPretty = false;

	private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	static JSONDateFormat defaultDateFormat = new JSONDateFormat(DEFAULT_DATE_FORMAT);
	
	static BidDefaultSerializerProvider sp = new BidDefaultSerializerProvider();
	static {
		sp.setNullValueSerializer(NullSerializer.instance);
		
	}
	
	/**
	 * 增加ObjectMapper 对象池模式，提高性能
	 */
	public static ConcurrentLinkedQueue<ObjectMapper> mapperQueue = new ConcurrentLinkedQueue<ObjectMapper>();
	
	/**
	 * 获取mapper对象
	 *
	 * @author 	: <a href="mailto:dejianliu@ebnew.com">liudejian</a>  2014-12-10 下午2:28:03
	 * @return
	 */
	private static ObjectMapper getObjectMapper() {
		ObjectMapper mapper = mapperQueue.poll();
		if(mapper == null) {
			mapper = new  ObjectMapper(null, sp, null);
			//将数字作来字符串输出(1.为了兼容json-lib-2.4-jdk1.5, 2.长整型在返回前端页面时JS无法处理，将丢失后面的尾数)
			mapper.configure(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS, Boolean.TRUE);
			mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
			mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
			// mapper.setSerializationInclusion(JsonInclude.Include.NON_DEFAULT);
			mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			mapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
			mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
			mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
			mapper.configure(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS, false);
 		}
		return mapper;
	}
	/**
	 * 返回ObjectMapper对象
	 *
	 * @author 	: <a href="mailto:dejianliu@ebnew.com">liudejian</a>  2014-12-10 下午2:28:14
	 * @param mapper
	 */
	private static void returnMapper(ObjectMapper mapper) {
		if(mapper != null) {
			mapperQueue.offer(mapper);
		}
	}
	
	public static boolean isPretty() {
		return isPretty;
	}

	public static void setPretty(boolean isPretty) {
		JsonUtils.isPretty = isPretty;
	}

	/**
	 * JSON串转换为Java泛型对象，可以是各种类型，此方法最为强大。用法看测试用例。
	 * 
	 * @param <T>
	 * @param jsonString
	 *            JSON字符串
	 * @param tr
	 *            TypeReference,例如: new TypeReference< List<FamousUser> >(){}
	 * @return List对象列表
	 */
	@SuppressWarnings("unchecked")
	public static <T> T json2GenericObject(String jsonString,TypeReference<T> tr, String dateFormat) {
		ObjectMapper objectMapper = null;
		if (StringUtils.isNotEmpty(jsonString)) {
			try {
				objectMapper = getObjectMapper();
				objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

				if (StringUtils.isBlank(dateFormat)) {
					objectMapper.setDateFormat(defaultDateFormat);
				} else {
					objectMapper.setDateFormat(new JSONDateFormat(dateFormat));
				}
				return (T) objectMapper.readValue(jsonString, tr);
			} catch (Exception e) {
 				log.error(e.getMessage(), e);
			} finally {
				returnMapper(objectMapper);
			}
		}
		return null;
	}
	
	/**
	 * Json字符串转Java对象
	 * 
	 * @param jsonString
	 * @param c
	 * @return
	 */
	public static <T> T json2Object(String jsonString, Class<T> c,String dateFormat) {
		ObjectMapper objectMapper = null;
		if (StringUtils.isNotEmpty(jsonString)) {
			try {
				objectMapper = getObjectMapper(); 
				objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
				if (StringUtils.isEmpty(dateFormat)) {
					objectMapper.setDateFormat(defaultDateFormat);
				} else {
					objectMapper.setDateFormat(new JSONDateFormat(dateFormat));
				}
				return (T)objectMapper.readValue(jsonString, c);
			} catch (Exception e) {
 				log.error(e.getMessage(), e);
			} finally {
				returnMapper(objectMapper);
			}
		}
		return null;
	}
	/**
	 * Json字符串转Java对象
	 *
	 * @param jsonString
	 * @param c
	 * @return
	 */
	public static <T> T json2Object(String jsonString, Class<T> c) {
		return  json2Object(jsonString,c,null);
	}

	/**
	 * Java对象转Json字符串
	 * 
	 * @param object
	 *            目标对象
	 * @param executeFields
	 *            排除字段
	 * @param includeFields
	 *            包含字段
	 * @param dateFormat
	 *            时间格式化
	 * @return
	 */
	public static String toJson(Object object, String[] executeFields,
			String[] includeFields, String dateFormat) {
		String jsonString = "";
		ObjectMapper objectMapper = null;
		try {
			BidBeanSerializerFactory bidBeanFactory = BidBeanSerializerFactory.instance;
			objectMapper = getObjectMapper(); 
		 
			if (StringUtils.isEmpty(dateFormat)) {
				objectMapper.setDateFormat(defaultDateFormat);
			} else {
				objectMapper.setDateFormat(new JSONDateFormat(dateFormat));
			}
			if (includeFields != null) {
				String filterId = "includeFilter";
				objectMapper.setFilters(new SimpleFilterProvider().addFilter(filterId, SimpleBeanPropertyFilter
								.filterOutAllExcept(includeFields)));
				bidBeanFactory.setFilterId(filterId);
				objectMapper.setSerializerFactory(bidBeanFactory);
 
			} else if (includeFields == null && executeFields != null) {
				String filterId = "executeFilter";
				objectMapper.setFilters(new SimpleFilterProvider().addFilter(
						filterId, SimpleBeanPropertyFilter
								.serializeAllExcept(executeFields)));
				bidBeanFactory.setFilterId(filterId);
 			}
 			if (isPretty) {
				jsonString = objectMapper.writerWithDefaultPrettyPrinter()
						.writeValueAsString(object);
			} else {
				jsonString = objectMapper.writeValueAsString(object);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.warn(e.getMessage(), e);
		} finally {
			returnMapper(objectMapper);
		}
		return jsonString;
	}
	/**
	 * Java对象转Json字符串
	 *
	 * @param object
	 *            目标对象
	 * @return
	 */
	public static String toJson(Object object) {
		return  toJson(object,(String[])null,(String[])null,null);
	}
	

	public static void main(String[] args) throws ParseException {
		// App a = new App();
		// a.setAppIp("123123");
		// a.setAppName("中华人民共和国");
		// Person person = new Person();
		// person.setDifTime(5000);
		// Map<String, Object> dataMap = new HashMap<String, Object>();
		// dataMap.put(Person.class.getName(), person);
		// a.setDataMap(dataMap);
		// a.setCreateTime(new Date());
		// String aa = JsonUtils.toJson(a, new String[] { "id"},null,
		// "yyyy-MM-dd");
		// System.out.println(aa);
		// App app = JsonUtils.json2Object(aa, App.class, "yyyy-MM-dd");
		// System.out.println(ToStringBuilder.reflectionToString(app));

		Map<String, Object> mapV = new HashMap<String, Object>();
		mapV.put("liu", "hello");
		mapV.put("age", "31");
		mapV.put("address", "四川省");
		mapV.put("date", new Date());
		String[] str=new String[1];
		str[0]="password";
		Person p=new Person();
		p.setName("11");
		p.setPassword("22");
		String res = JsonUtils.toJson(mapV, str, null, "yyyy-MM-dd HH:mm:ss");
		String res1 = JsonUtils.toJson(p, null, str, "yyyy-MM-dd HH:mm:ss");
		System.out.println("---"+res1);

		System.out.println(res);
		HashMap<?, ?> map = JsonUtils.json2Object(res, HashMap.class, "yyyy-MM-dd HH:mm:ss");
		System.out.println(map.get("date"));

		JSONDateFormat fm = new JSONDateFormat("yyyy-MM-dd");
		System.out.println(fm.format(new Date()));
		System.out.println(fm.parse(fm.format(new Date())));

	}

}
