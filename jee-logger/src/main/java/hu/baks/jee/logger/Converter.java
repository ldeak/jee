package hu.baks.jee.logger;

public interface Converter {
	Object convert(Object input);
	
	class DEFAULT implements Converter {
		@Override
		public Object convert(Object input) {
			return input;
		}
	}
}
