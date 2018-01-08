package hu.baks.jee.logger;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.LogManager;

import javax.ejb.Stateless;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import hu.baks.jee.util.ReflectionUtil;

@Stateless
public class LoggerService {
	
	@AroundInvoke
	public Object log(InvocationContext context) throws Exception {
		Object result;

		Map<String, Object> beans = initBeans(context);
		List<LogHandler<?>> handlers = resolveHandlers(context.getMethod(), beans);

		handlers.forEach(h -> h.onCall());
		try {
			result = context.proceed();
			if (context.getMethod().isAnnotationPresent(LogData.class)) {
				addData(context.getMethod(), result, beans);
			} else {
				beans.put(getLogged(context.getMethod()).resultKey(), result);
			}
			handlers.forEach(h -> h.onSuccess());
		} catch(Exception ex) {
			handlers.forEach(h -> h.onFailure(ex));
			throw ex;
		} finally {
			handlers.forEach(h -> h.onComplete());
		}
		return result;
	}
	
	private void log(String loggerName, String message) {
		java.util.logging.Logger logger = LogManager.getLogManager().getLogger(loggerName);
        logger.log(Level.INFO, message);
	}

	private Map<String, Object> initBeans(InvocationContext context) throws InstantiationException, IllegalAccessException {
		Map<String, Object> beans = new HashMap<>();
		Method method = context.getMethod();
		beans.put(getLogged(method).methodKey(), method);
		Parameter[] paramDefs = method.getParameters();
		for (int i = 0; i < paramDefs.length; i++) {
			Parameter p = paramDefs[i];
			if (p.isAnnotationPresent(LogData.class)) {
				addData(p, context.getParameters()[i], beans);
			}
		}
		return beans;
	}
	
	private Logged getLogged(Method method) {
		if (method.isAnnotationPresent(Logged.class)) {
			return method.getAnnotation(Logged.class);
		}
		return ReflectionUtil.defaultsOf(Logged.class);
	}

	private void addData(AnnotatedElement elem, Object value, Map<String, Object> beans) throws InstantiationException, IllegalAccessException {
		LogData data = elem.getAnnotation(LogData.class);
		Converter conv = data.converter().newInstance();
		beans.put(data.value(), conv.convert(value));
	}
	
	private List<LogHandler<?>> resolveHandlers(Method method, Map<String, Object> beans) throws InstantiationException, IllegalAccessException {
		List<LogHandler<?>> handlers = new ArrayList<>();
		for (Annotation a : method.getAnnotations()) {
			if (a.annotationType().isAnnotationPresent(Logger.class)) {
				Logger logger = a.annotationType().getAnnotation(Logger.class);
				LogHandler<?> handler = logger.handler().newInstance();
				handler.init(a, beans, this::log);
				handlers.add(handler);
			}
		}
		return handlers;
	}
}
