package pl.edu.agh.to.game.remoteproxy.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.edu.agh.to.game.common.Controller;
import pl.edu.agh.to.game.common.GameBuilder;
import pl.edu.agh.to.game.common.Observer;

public class TestGameBuilder implements GameBuilder {
	private Map<Integer, Controller> controllers;
	private List<Observer> observers;
	private int requiredControllers;
	private int requiredObservers;
	private int carId;

	public TestGameBuilder(int requiredControllers, int requiredObservers) {
		this.controllers = new HashMap<Integer, Controller>();
		this.observers = new ArrayList<>();
		this.requiredControllers = requiredControllers;
		this.requiredObservers = requiredObservers;
		this.carId = 0;
	}

	public int registerController(Controller controller) {
		carId++;
		controllers.put(carId,controller);
		System.out.println("pl.edu.agh.to.game.common.Controller registered");
		return carId;
	}

	public void registerObserver(Observer observer) {
		observers.add(observer);
		System.out.println("pl.edu.agh.to.game.common.Observer registered");
	}

	public int requiredControllers() {
		return requiredControllers;
	}

	public int requiredObservers() {
		return requiredObservers;
	}

	public Map<Integer, Controller> getControllers() {
		return controllers;
	}

	public List<Observer> getObservers() {
		return observers;
	}



}
