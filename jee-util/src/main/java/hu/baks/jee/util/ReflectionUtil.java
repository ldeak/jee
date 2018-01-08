package hu.baks.jee.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ReflectionUtil {

	private ReflectionUtil() {
		throw new UnsupportedOperationException();
	}
	
	public static <A extends Annotation> A defaultsOf(Class<A> annotationType) {
		return annotationType.cast(
				Proxy.newProxyInstance(annotationType.getClassLoader(), new Class[] { annotationType }, new Defaults()));
	}

	static class Defaults implements InvocationHandler {
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			return method.getDefaultValue();
		}
	}
}
