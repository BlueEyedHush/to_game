package pl.edu.agh.to.game.server;

/**
 * Created by blueeyedhush on 10.12.15.
 */
public class Bootstraper {
    public static void main(String[] args) {
        if(args.length < 1) {
            GameServer.LOGGER.error("Map name is required");
            return;
        }

        GameServer server = new GameServer();
        try {
            server.run(args[0]);
        } catch (Exception e) {
            GameServer.LOGGER.error("Aborting", e);
        }
    }
}
