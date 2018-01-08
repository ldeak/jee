package hu.baks.jee.logger.impl;

import java.util.HashMap;
import java.util.Map;

import hu.baks.jee.logger.LogHandler.AbstractLogHandler;

public class SlowMethodHandler extends AbstractLogHandler<SlowMethod> {
	
	private long start;
	
	@Override
	public void onCall() {
		start = System.currentTimeMillis();
	}
	
	@Override
	public void onComplete() {
		long duration = System.currentTimeMillis() - start;
		if (duration >= config.limit()) {
			Map<String, Object> params = new HashMap<>(beans);
			params.put("duration", duration);
			String message;
			try {
				message = config.resolver().newInstance().getValue(config.message(), params);
			} catch (InstantiationException | IllegalAccessException e) {
				throw new RuntimeException(e);
			}
			callback.log(config.logger(), message);
		}
	}
}
