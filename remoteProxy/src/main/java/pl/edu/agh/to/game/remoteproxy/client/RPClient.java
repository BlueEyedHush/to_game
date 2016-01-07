package pl.edu.agh.to.game.remoteproxy.client;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Enumeration;

import pl.edu.agh.to.game.remoteproxy.config.RemoteConfig;
import pl.edu.agh.to.game.remoteproxy.server.ServerService;

public class RPClient {

	private ClientRemoteObject client;
	private ClientActionHandler handler;
	private ClientType type;
	private ServerService server;

	public RPClient(ClientActionHandler handler, ClientType type)
			throws RemoteException, NotBoundException {
		this(handler, type, RemoteConfig.SERVER_HOST);
	}

	public RPClient(ClientActionHandler handler, ClientType type,
			String serverIP) throws RemoteException, NotBoundException {
		setRmiHostname();
		Registry registry = LocateRegistry.getRegistry(
				RemoteConfig.SERVER_HOST, RemoteConfig.PORT);
		Remote lookup = registry.lookup(RemoteConfig.RMI_ID);
		server = (ServerService) lookup;
		this.handler = handler;
		this.type = type;
		this.client = new ClientRemoteObject(handler, type);
		server.handleConnect(this.client);
	}

	public void makeMove(Integer move) {
		client.setNextMove(move);
	}

	private void setRmiHostname() {
		System.setProperty("java.net.preferIPv4Stack", "true");
//		System.setProperty("java.rmi.server.hostname", "10.20.118.122");
		try {
			Enumeration<NetworkInterface> interfaces = NetworkInterface
					.getNetworkInterfaces();
			while (interfaces.hasMoreElements()) {
				NetworkInterface iface = interfaces.nextElement();
				// filters out 127.0.0.1 and inactive interfaces
				if (iface.isLoopback() || !iface.isUp())
					continue;

				Enumeration<InetAddress> addresses = iface.getInetAddresses();
				InetAddress addr = addresses.nextElement();

				System.out.println("Using " + addr.getHostAddress()
						+ " as hostname");
				System.setProperty("java.rmi.server.hostname",
						addr.getHostAddress());

			}
		} catch (SocketException e) {
			throw new RuntimeException(e);
		}

	}
}
