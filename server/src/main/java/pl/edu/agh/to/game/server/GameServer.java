package pl.edu.agh.to.game.server;

import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.to.game.bot.factory.BotFactory;
import pl.edu.agh.to.game.common.Controller;
import pl.edu.agh.to.game.common.Observer;
import pl.edu.agh.to.game.common.state.Board;
import pl.edu.agh.to.game.common.state.CarState;
import pl.edu.agh.to.game.common.state.GameState;
import pl.edu.agh.to.game.common.state.Vector;
import pl.edu.agh.to.game.remoteproxy.server.RPServer;
import pl.edu.agh.to.game.server.helpers.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class GameServer {
    public static final Logger LOGGER = LoggerFactory.getLogger(GameServer.class);

    static {
        BasicConfigurator.configure();
    }

    private static final String MAP_FILE_EXT = ".map";
    private static final String METADATA_FILE_EXT = ".meta";

    private GameState state = null;
    private final Map<Integer, Controller> controllers = new HashMap<>();
    private final List<Observer> observers = new ArrayList<>();

    public void run(String mapName) throws Exception {
        Path mapPath = Paths.get(mapName + MAP_FILE_EXT);
        Path metaPath = Paths.get(mapName + METADATA_FILE_EXT);

        boolean[][] rawBoard = MapReader.getBoard(mapPath);
        Pair<Vector, List<Metadata>> metadatas = MetaReader.readMetadataFrom(metaPath);
        Board board = buildAndVerifiyBoard(rawBoard, metadatas.first(), metadatas.second());

        buildInitialGamestate(board, metadatas.second());
        GameBuilderImpl gameBuilder = new GameBuilderImpl(state, controllers, observers);
        RPServer remoteProxy = new RPServer();
        remoteProxy.initialize(gameBuilder);
        Game game = gameBuilder.build();
        game.startGame();
    }

    private Board buildAndVerifiyBoard(boolean[][] rawBoard, Vector finish, List<Metadata> carInfo) {
        Board board = new Board(finish, rawBoard);

        if(finish.getX() > board.getMaxX() || finish.getY() > board.getMaxY()) {
            throw new RuntimeException("Finish outside of board");
        }

        for(Metadata m: carInfo) {
            int initX = m.getInitialPosition().getX();
            int initY = m.getInitialPosition().getY();
            if(initX < 0 || initY < 0 || initX > board.getMaxX() || initY > board.getMaxY()) {
                throw new RuntimeException("Car placed outside of the board");
            }

            if(!board.get(m.getInitialPosition())) {
                throw new RuntimeException("Car placed on inactive position");
            }
        }

        return board;
    }

    private void buildInitialGamestate(Board board, List<Metadata> metadatas) {
        final int carNum = metadatas.size();
        Map<Integer, CarState> carStates = new HashMap<>(carNum);

        /* sort metadatas so that bots are first - will simplify id generations for player controllers later
        *  so isBot == true must go first, but natural order is false < true -> reversed order is needed */
        metadatas.sort(Comparator.comparing(Metadata::isBot, Comparator.reverseOrder()));

        SequenceGenerator idGen = new SequenceGenerator();
        for(Metadata metadata: metadatas) {
            int id = idGen.next();
            CarState carState = new CarState(metadata.getInitialPosition());
            carStates.put(id, carState);
            if(metadata.isBot()) {
                Controller botController = BotFactory.createBot(metadata.getAlgorithmId().get());
                controllers.put(id, botController);
            }
        }

        state = new GameState(carStates, board);
    }
}
