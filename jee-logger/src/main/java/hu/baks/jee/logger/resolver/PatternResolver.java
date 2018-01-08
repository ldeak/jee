package hu.baks.jee.logger.resolver;

import java.util.Map;

public interface PatternResolver {
	String getValue(String pattern, Map<String, Object> beans);
}
