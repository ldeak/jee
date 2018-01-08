package hu.baks.jee.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Unit test for {@link StringUtil}
 */
public class StringUtilTest {
	
	@Test
	public void testIsEmptyWithNull() {
		Assertions.assertTrue(StringUtil.isEmpty(null));
	}
	
	@Test
	public void testIsEmptyWithEmptyString() {
		Assertions.assertTrue(StringUtil.isEmpty(""));
	}
	
	@Test
	public void testIsEmptyWithWhitespace() {
		Assertions.assertFalse(StringUtil.isEmpty(" "));
	}
	
	@Test
	public void testIsEmptyWithChars() {
		Assertions.assertFalse(StringUtil.isEmpty(" asd "));
	}

	@Test
	public void testIsBlankWithNull() {
		Assertions.assertTrue(StringUtil.isBlank(null));
	}
	
	@Test
	public void testIsBlankWithEmptyString() {
		Assertions.assertTrue(StringUtil.isBlank(""));
	}
	
	@Test
	public void testIsBlankWithWhitespace() {
		Assertions.assertTrue(StringUtil.isBlank(" "));
	}
	
	@Test
	public void testIsBlankWithChars() {
		Assertions.assertFalse(StringUtil.isEmpty(" asd "));
	}
}
