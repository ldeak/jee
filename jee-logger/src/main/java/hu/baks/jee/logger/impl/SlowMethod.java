package hu.baks.jee.logger.impl;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import hu.baks.jee.logger.Logger;
import hu.baks.jee.logger.resolver.PatternResolver;
import hu.baks.jee.logger.resolver.SimpleResolver;

@Retention(RUNTIME)
@Target(METHOD)
@Logger(handler=SlowMethodHandler.class)
public @interface SlowMethod {
	String logger() default "";
	int limit() default 0;
	String message() default "Execution time: #{duration} ms.";
	Class<? extends PatternResolver> resolver() default SimpleResolver.class;
}
