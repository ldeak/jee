package hu.baks.jee.util;

import java.util.Map;

/**
 * String utility methods.
 */
public class StringUtil {

	private StringUtil() {
		throw new UnsupportedOperationException();
	}

	public static boolean isEmpty(String param) {
		return param == null || param.isEmpty();
	}
	
	public static boolean isBlank(String param) {
		return isEmpty(param) || param.trim().isEmpty();
	}
	
	public static String toString(Object param, String defaultValue) {
		return param == null ? defaultValue : String.valueOf(param);
	}
	
	public static String replace(String template, Map<String, Object> params) {
		return replace(template, null, null, params);
	}

	public static String replace(String template, String prefix, String postfix, Map<String, Object> params) {
		String variablePrefix = toString(prefix, "#{");
		String variablePostfix = toString(postfix, "}");
		return params.entrySet().stream()
				.reduce(template, (s, e) -> s.replace(variablePrefix + e.getKey() + variablePostfix, toString(e.getValue(), "")), (s, s2) -> s);
	}
}
