package ru.javabegin.training.spring.impls.pool;

import org.springframework.beans.factory.annotation.Autowired;

import ru.javabegin.training.spring.enums.ColorStyle;
import ru.javabegin.training.spring.interfaces.Robot;
import ru.javabegin.training.spring.qualifiers.RobotType;

public class T1000Pool {

	public T1000Pool() {
		System.out.println("t1000 pool constructor()");
	}

	@Autowired
	@RobotType(sound = false, color = ColorStyle.BLACK)
	private Robot blackRobot;

	@Autowired
	@RobotType(sound = true, color = ColorStyle.BLACK)
	private Robot blackSoundableRobot;

	public Robot getBlackRobot() {
		return blackRobot;
	}

	public Robot getBlackSoundableRobot() {
		return blackSoundableRobot;
	}

}
