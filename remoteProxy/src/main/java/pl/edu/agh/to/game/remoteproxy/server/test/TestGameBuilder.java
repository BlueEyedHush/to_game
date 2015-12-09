package pl.edu.agh.to.game.remoteproxy.server.test;

import java.util.ArrayList;
import java.util.List;

import pl.edu.agh.to.game.common.Controller;
import pl.edu.agh.to.game.common.GameBuilder;
import pl.edu.agh.to.game.common.Observer;

public class TestGameBuilder extends GameBuilder {
	private List<Controller> controllers;
	private List<Observer> observers;
	private int requiredControllers;
	private int requiredObservers;
	private int carId;

	public TestGameBuilder(int requiredControllers, int requiredObservers) {
		this.controllers = new ArrayList<>();
		this.observers = new ArrayList<>();
		this.requiredControllers = requiredControllers;
		this.requiredObservers = requiredObservers;
		this.carId = 0;
	}
	
	public int registerController(Controller controller) {
		controllers.add(controller);
		System.out.println("Controller registered");
		carId++;
		return carId;
	}
	
	public void registerObserver(Observer observer) {
		observers.add(observer);
		System.out.println("Observer registered");
	}
	
	public int requiredControllers() {
		return requiredControllers;
	}
	
	public int requiredObservers() {
		return requiredObservers;
	}

	public List<Controller> getControllers() {
		return controllers;
	}

	public List<Observer> getObservers() {
		return observers;
	}
	
	
	
}
