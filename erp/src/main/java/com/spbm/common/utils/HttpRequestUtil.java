package com.spbm.common.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 *
 */
public class HttpRequestUtil {

	public static HttpServletRequest getHttpServletRequest() {
		return ((ServletRequestAttributes) (RequestContextHolder
				.getRequestAttributes())).getRequest();
	}

	/**
	 * @return requestAttributes
	 */
	public static RequestAttributes getRequestAttributes() {
		return RequestContextHolder.getRequestAttributes();
	}

	/**
	 * @param key
	 * @param value
	 * @param scope
	 */
	public static void setAttribute(String key, Object value, int scope) {
		RequestContextHolder.getRequestAttributes().setAttribute(key, value,
				scope);
	}

	/**
	 * @param key
	 * @param scope
	 * @return attribute
	 */
	public static Object getAttribute(String key, int scope) {
		return RequestContextHolder.getRequestAttributes().getAttribute(key,
				scope);
	}

	/**
	 * @param key
	 * @param scope
	 */
	public static void removeAttribute(String key, int scope) {
		RequestContextHolder.getRequestAttributes().removeAttribute(key, scope);
	}

	public static String getHostNameForLiunx() {
		try {
			return (InetAddress.getLocalHost()).getHostName();
		} catch (UnknownHostException uhe) {
			String host = uhe.getMessage(); // host = "hostname: hostname"
			if (host != null) {
				int colon = host.indexOf(':');
				if (colon > 0) {
					return host.substring(0, colon);
				}
			}
			return "UnknownHost";
		}
	}

	public static String getHostName() {
		if (System.getenv("COMPUTERNAME") != null) {
			return System.getenv("COMPUTERNAME");
		} else {
			return getHostNameForLiunx();
		}
	}
}