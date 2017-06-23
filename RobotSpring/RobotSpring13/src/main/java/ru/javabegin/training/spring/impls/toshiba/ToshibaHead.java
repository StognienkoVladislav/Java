package ru.javabegin.training.spring.impls.toshiba;

import org.springframework.stereotype.Component;

import ru.javabegin.training.spring.interfaces.Head;

@Component
public class ToshibaHead implements Head {

	public void calc() {
		System.out.println("Thinking about Toshiba...");
	}

}
