package ru.javabegin.training.spring.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ru.javabegin.training.spring.impls.pool.T1000Pool;

public class Start {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("all_context.xml");

		T1000Pool t1000GoldenPool = (T1000Pool) context.getBean("t1000ColdenPool");
		t1000GoldenPool.beginShow();

	}
}
