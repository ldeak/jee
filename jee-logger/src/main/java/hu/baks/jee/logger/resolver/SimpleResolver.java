package hu.baks.jee.logger.resolver;

import java.util.Map;

import hu.baks.jee.util.StringUtil;

public class SimpleResolver implements PatternResolver {
	@Override
	public String getValue(String pattern, Map<String, Object> beans) {
		return StringUtil.replace(pattern, beans);
	}
}
