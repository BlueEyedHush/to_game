package pl.edu.agh.to.game.remoteproxy.config;

public class RemoteConfig {

	public static final String RMI_ID = "RemoteProxy";
	public static final int PORT = 22222;
	public static final String SERVER_HOST = "localhost";
	public static final String URL = "rmi://" + SERVER_HOST + "/" + RMI_ID;
	public static final long TIME_STEP = 3 * 1000;
	public static final long TIMEOUT = 60 * 1000;
}
