package com.fox.cradle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class CradleApplication {

	public static void main(String[] args)
	{
		SpringApplication.run(CradleApplication.class, args);
	}

	static boolean isAdult(int age)
	{
		if(age > 18) return  true;
		else return  false;
	}

	static boolean isEvenNumber(int n)
	{
		if(n%2 == 0)
		{
			return true;
		}
		else return false;
	}

	static int addNumbers(int a, int b)
	{
		return a + b;
	}

	static int multiplayNumbers(int a, int b)
	{
		return a * b;
	}

	static String convertToHuge(String s )
	{
		return  s.toUpperCase();
	}

	static boolean isPositivNumber(int number)
	{
		if(number >= 0)
		{
			return true;
		}
		else return false;
	}
}
