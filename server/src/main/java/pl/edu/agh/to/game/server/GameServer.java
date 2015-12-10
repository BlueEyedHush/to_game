package pl.edu.agh.to.game.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.to.game.common.Controller;
import pl.edu.agh.to.game.common.GameBuilder;
import pl.edu.agh.to.game.common.Observer;
import pl.edu.agh.to.game.common.state.*;
import pl.edu.agh.to.game.server.helpers.*;
import pl.edu.agh.to.game.bot.factory;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GameServer {
    private static Logger LOGGER = LoggerFactory.getLogger(GameServer.class);

    private static String MAP_FILE_EXT = ".map";
    private static String METADATA_FILE_EXT = ".meta";

    private GameState state = null;
    private Map<Integer, Controller> controllers = null;
    private List<Observer> observers = null;

    public static void main(String[] args) throws Exception {
        if(args.length < 1) {
            LOGGER.error("Map name is required");
            return;
        }

        Path mapPath = Paths.get(args[0], MAP_FILE_EXT);
        Path metaPath = Paths.get(args[0], METADATA_FILE_EXT);

        boolean[][] rawBoard = MapReader.getBoard(mapPath);
        Pair<Vector, List<Metadata>> metadatas = MetaReader.readMetadataFrom(metaPath);
        Board board = new Board(rawBoard.length, rawBoard[0].length, metadatas.first(), rawBoard);

        GameServer server = new GameServer();
        server.buildInitialGamestate(board, metadatas.second());
        GameBuilder gameBuilder = new GameBuilder(server.state, server.controllers, server.observers);
        RPServer remoteProxy = new RPServer(gameBuilder);
        remoteProxy.initialize();
        Game game = gameBuilder.build();
        game.startGame();
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
