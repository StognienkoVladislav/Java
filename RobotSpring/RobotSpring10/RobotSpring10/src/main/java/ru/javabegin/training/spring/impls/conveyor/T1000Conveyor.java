package ru.javabegin.training.spring.impls.conveyor;

import ru.javabegin.training.spring.interfaces.RobotConveyor;
import ru.javabegin.training.spring.interfaces.Robot;

public abstract class T1000Conveyor implements RobotConveyor {

	@Override
	public abstract Robot createRobot();

}
