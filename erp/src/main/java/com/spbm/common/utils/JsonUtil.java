package com.spbm.common.utils;

import java.io.IOException;

import org.apache.commons.logging.Log;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * class util to process json data.
 */
public class JsonUtil {
	public static final ObjectMapper om = new ObjectMapper();

	/**
	 * @param data
	 * @param clazz
	 * @return null
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getEntity(Object data, Class<T> clazz) {
		if (data.getClass() == clazz) {
			return (T) data;
		}
		try {
			om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			String json = om.writeValueAsString(data);
			return om.readValue(json, clazz);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * @param data
	 * @return null
	 */
	public static String getJson(Object data) {
		try {
			return om.writeValueAsString(data);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * @param json
	 * @param clazz
	 * @return null
	 */
	public static <T> T getEntity(String json, Class<T> clazz) {
		try {
			return getEntity(json, clazz, false);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * @param json
	 * @param javatype
	 * @return null
	 */
	public static <T> T getEntity(String json, JavaType javatype) {
		try {
			return getEntity(json, javatype, false);
		} catch (Exception e) {
			return null;
		}
	}


	/**
	 * @param json
	 * @param clazz
	 * @return null
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	public static <T> T getEntity(String json, Class<T> clazz, boolean failOnUnkownProperites)
			throws JsonParseException, JsonMappingException, IOException {
		om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, failOnUnkownProperites);
		return om.readValue(json, clazz);
	}

	/**
	 * @param json
	 * @param javatype
	 * @return null
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	public static <T> T getEntity(String json, JavaType javatype, boolean failOnUnkownProperites) throws JsonParseException, JsonMappingException, IOException {
		om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, failOnUnkownProperites);
		return om.readValue(json, javatype);
	}
}