package com.fox.cradle;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CradleApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void returnTrueAgeTest()
	{
		//given
		boolean result = true;
		//when
		boolean actual = CradleApplication.isAdult(22);
		//then
		boolean expected = true;
		//result
		Assertions.assertEquals(expected, actual);
	}

	@Test
	void returnFalseAgeTest()
	{
		//given
		boolean result = false;
		//when
		boolean actual = CradleApplication.isAdult(5);
		//then
		boolean expected = false;
		//test
		Assertions.assertEquals(expected, actual);
	}

}
