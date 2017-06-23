package ru.javabegin.training.spring.impls.toshiba;

import org.springframework.stereotype.Component;

import ru.javabegin.training.spring.interfaces.Leg;

@Component
public class ToshibaLeg implements Leg {

	public void go() {
		System.out.println("Go to Toshiba!");
	}

}
