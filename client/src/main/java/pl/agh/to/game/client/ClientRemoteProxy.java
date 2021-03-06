package pl.agh.to.game.client;

import pl.edu.agh.to.game.common.state.Board;
import pl.edu.agh.to.game.remoteproxy.client.ClientActionHandler;
import pl.edu.agh.to.game.remoteproxy.client.ClientType;


public class ClientRemoteProxy {

    private ClientType clientType;
    private Board board;
    private ClientActionHandler actionHandler;
    private String IP;

    public ClientRemoteProxy(ClientActionHandler actionHandler, ClientType clientType, String IP) {
        this.clientType = clientType;
        this.actionHandler = actionHandler;
        this.IP = IP;
    }

    public void tellClientToMove(){};
    public void tellClientSomeoneMoved() {};
    public void tellClientGameOver() {};

    public Board getBoard() {
        return board;
    }

    public void mockActions() {
        return;
    }
}
