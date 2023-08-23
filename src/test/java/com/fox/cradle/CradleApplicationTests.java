package com.fox.cradle;

import org.h2.command.ddl.CreateAggregate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.swing.*;

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

	@Test
	void returnTrueIfEven()
	{
		boolean result = true;
		boolean actual = CradleApplication.isEvenNumber(2);
		boolean expected = true;
		Assertions.assertTrue(expected);
	}
	@Test
	void returnFalseIfOdd()
	{
		boolean result = false;
		boolean actual = CradleApplication.isEvenNumber(3);
		boolean expected = false;
		Assertions.assertFalse(expected);
	}

	@Test
	void returnIntAddTwoNumbers()
	{
		int a = 1;
		int b = 2;

		int actual = CradleApplication.addNumbers(a , b);
		int expected = 3;
		Assertions.assertEquals(expected, actual);
	}

	@Test
	void returnMultiplliedNumber()
	{
		int a = 5;
		int b = -2;

		int actual = CradleApplication.multiplayNumbers(a, b);
		int expected = -10;
		Assertions.assertEquals(expected, actual);
	}

	@Test
	void returnStringUpperCase()
	{
		String s = "test";
		String actual = CradleApplication.convertToHuge(s);
		String expected = "TEST";
		Assertions.assertEquals(expected, actual);
	}
	@Test
	void returnStringUpperCaseWithUppercaseStart()
	{
		String s = "Test";
		String actual = CradleApplication.convertToHuge(s);
		String expected = "TEST";
		Assertions.assertEquals(expected, actual);
	}

	@Test
	void returnTrueIfPositiv()
	{
		int n = 5;
		boolean actual = CradleApplication.isPositivNumber(n);
		boolean expected = true;
		Assertions.assertEquals(expected, actual);
	}

	@Test
	void returnFalseIfNegativ()
	{
		int n = -5;
		boolean actual = CradleApplication.isPositivNumber(n);
		boolean expected = false;
		Assertions.assertEquals(expected, actual);
	}
}
