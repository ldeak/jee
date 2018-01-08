package hu.baks.jee.logger.resolver;

import java.util.Collections;
import java.util.Map;

import javax.el.BeanNameResolver;

public class MapBasedBeanResolver extends BeanNameResolver {
	private final Map<String, Object> beanMap;
	
	public MapBasedBeanResolver(Map<String, Object> beanMap) {
		this.beanMap = Collections.unmodifiableMap(beanMap);
	}
	
	@Override
	public boolean isNameResolved(String beanName) {
		return beanMap.containsKey(beanName);
	}
	
	@Override
	public Object getBean(String beanName) {
		return beanMap.get(beanName);
	}
}
