package hu.baks.jee.logger;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target({ METHOD, PARAMETER })
public @interface LogData {
	String value();
	Class<? extends Converter> converter() default Converter.DEFAULT.class;
}
