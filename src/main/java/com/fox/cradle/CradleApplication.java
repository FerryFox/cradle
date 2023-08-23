package com.fox.cradle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
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
}
