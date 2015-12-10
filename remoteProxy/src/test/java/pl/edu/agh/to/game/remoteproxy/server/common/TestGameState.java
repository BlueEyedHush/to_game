//package pl.edu.agh.to.game.remoteproxy.server.common;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import pl.edu.agh.to.game.common.state.Board;
//import pl.edu.agh.to.game.common.state.CarState;
//import pl.edu.agh.to.game.common.state.GameState;
//
//public class TestGameState extends GameState {
//
//	private static final long serialVersionUID = 598704274522208217L;
//
//	private Board board;
//
//	private int currentCarId;
//
//	private Map<Integer, CarState> carStates = new HashMap<Integer, CarState>();
//
//	public TestGameState() {
//		super();
//	}
//
//	public TestGameState(Board board, int currentCarId, Map<Integer, CarState> carStates) {
//		super();
//		this.board = board;
//		this.currentCarId = currentCarId;
//		this.carStates = carStates;
//	}
//
//	@Override
//	public Board getBoard() {
//		return board;
//	}
//
//	public void setBoard(Board board) {
//		this.board = board;
//	}
//
//	@Override
//	public int getCurrentCarId() {
//		return currentCarId;
//	}
//
//	public void setCurrentCarId(int currentCarId) {
//		this.currentCarId = currentCarId;
//	}
//
//	@Override
//	public Map<Integer, CarState> getCarStates() {
//		return carStates;
//	}
//
//	public void setCarStates(Map<Integer, CarState> carStates) {
//		this.carStates = carStates;
//	}
//
//	@Override
//	public CarState getCarById(int id) {
//		return carStates.get(id);
//	}
//
//	@Override
//	public String toString() {
//		return "TestGameState [board=" + board + ", currentCarId=" + currentCarId + ", carStates=" + carStates + "]";
//	}
//
//}
