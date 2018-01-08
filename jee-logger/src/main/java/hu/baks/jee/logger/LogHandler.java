package hu.baks.jee.logger;

import java.lang.annotation.Annotation;
import java.util.Map;

public interface LogHandler<A extends Annotation> {
	
	void init(Annotation config, Map<String, Object> beans, LoggerCallback callback);
	
	default void onCall() { };
	default void onSuccess() { };
	default void onFailure(Exception ex) { };
	default void onComplete() { };
	
	class AbstractLogHandler<A extends Annotation> implements LogHandler<A> {

		protected A config;
		protected Map<String, Object> beans;
		protected LoggerCallback callback;
		
		@SuppressWarnings("unchecked")
		@Override
		public void init(Annotation config, Map<String, Object> beans, LoggerCallback callback) {
			this.config = (A) config;
			this.beans = beans;
			this.callback = callback;
		}
		
	}
}
