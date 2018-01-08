package hu.baks.jee.logger;

@FunctionalInterface
public interface LoggerCallback {
	void log(String loggerName, String message);
}
