package pl.edu.agh.to.game.remoteproxy.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;

import pl.edu.agh.to.game.common.GameBuilder;
import pl.edu.agh.to.game.common.state.Board;
import pl.edu.agh.to.game.common.state.CarState;

public class ServerRemoteObject extends UnicastRemoteObject implements ServerService{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private GameBuilder builder;
	
	public ServerRemoteObject(GameBuilder builder) throws RemoteException {
		super();
		this.builder = builder;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handleConnect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Board getBoard() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Integer, CarState> getInitialCarStates() {
		// TODO Auto-generated method stub
		return null;
	}



}
