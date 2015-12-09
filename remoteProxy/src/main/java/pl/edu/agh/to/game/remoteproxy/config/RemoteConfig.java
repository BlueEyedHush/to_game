package pl.edu.agh.to.game.remoteproxy.config;

public class RemoteConfig {
	public static final String RMI_ID = "RemoteProxy";
	public static final int PORT = 22222;
	public static final String SERVER_HOST = "192.168.0.2";
	public static final String URL = "rmi://" + SERVER_HOST + "/" + RMI_ID;
}
