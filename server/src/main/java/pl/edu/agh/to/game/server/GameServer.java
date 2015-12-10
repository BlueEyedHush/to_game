package pl.edu.agh.to.game.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.to.game.common.Controller;
import pl.edu.agh.to.game.common.GameBuilder;
import pl.edu.agh.to.game.common.Observer;
import pl.edu.agh.to.game.common.state.*;
import pl.edu.agh.to.game.common.state.Vector;
import pl.edu.agh.to.game.server.helpers.*;
import pl.edu.agh.to.game.bot.factory;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class GameServer {
    public static Logger LOGGER = LoggerFactory.getLogger(GameServer.class);

    private static String MAP_FILE_EXT = ".map";
    private static String METADATA_FILE_EXT = ".meta";

    private GameState state = null;
    private Map<Integer, Controller> controllers = new HashMap<>();
    private List<Observer> observers = new ArrayList<>();

    public void run(String mapName) throws Exception {
        Path mapPath = Paths.get(mapName, MAP_FILE_EXT);
        Path metaPath = Paths.get(mapName, METADATA_FILE_EXT);

        boolean[][] rawBoard = MapReader.getBoard(mapPath);
        Pair<Vector, List<Metadata>> metadatas = MetaReader.readMetadataFrom(metaPath);
        Board board = buildAndVerifiyBoard(rawBoard, metadatas.first(), metadatas.second());

        buildInitialGamestate(board, metadatas.second());
        GameBuilder gameBuilder = new GameBuilder(state, controllers, observers);
        RPServer remoteProxy = new RPServer(gameBuilder);
        remoteProxy.initialize();
        Game game = gameBuilder.build();
        game.startGame();
    }

    private Board buildAndVerifiyBoard(boolean[][] rawBoard, Vector finish, List<Metadata> carInfo) {
        Board board = new Board(rawBoard.length, rawBoard[0].length, finish, rawBoard);
        for(Metadata m: carInfo) {
            if(board.get(m.getInitialPosition())) {
                throw new RuntimeException("Car placed on inactive position");
            }
        }

        return board;
    }

    private void buildInitialGamestate(Board board, List<Metadata> metadatas) {
        final int carNum = metadatas.size();
        Map<Integer, CarState> carStates = new HashMap<>(carNum);
        controllers = new HashMap<>(carNum);

        /* sort metadatas so that bots are first - will simplify id generations for player controllers later
        *  so isBot == true must go first, but natural order is false < true -> reversed order is needed */
        metadatas.sort(Comparator.comparing(Metadata::isBot, Comparator.reverseOrder()));

        SequenceGenerator idGen = new SequenceGenerator();
        for(Metadata metadata: metadatas) {
            int id = idGen.next();
            CarState carState = new CarState(metadata.getInitialPosition());
            carStates.put(id, carState);
            if(metadata.isBot()) {
                Controller botController = BotFactory.createBot(metadata.getAlgorithmId());
                controllers.put(id, botController);
            }
        }

        state = new GameState(carStates, board);
    }
}
