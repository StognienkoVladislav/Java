package ru.javabegin.training.spring.impls.toshiba;

import org.springframework.stereotype.Component;

import ru.javabegin.training.spring.interfaces.Hand;

@Component
public class ToshibaHand implements Hand {

	public void catchSomething() {
		System.out.println("Catched from Toshiba!");
	}

}
