package pl.edu.agh.to.game.remoteproxy.config;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class RemoteConfig {

	public static final String RMI_ID = "RemoteProxy";
	public static final int PORT = 22222;
	//public static final String SERVER_HOST = "10.22.107.164";
	//public static final String URL = "rmi://" + SERVER_HOST + "/" + RMI_ID;
	public static final long TIME_STEP = 1 * 1000;
	public static final long TIMEOUT = 120 * 1000;
	
	public static List<String> getIPAddresses() {
		try {
			return enumerationAsStream(NetworkInterface.getNetworkInterfaces())
					.flatMap(i -> i.getInterfaceAddresses().stream())
					.map(a -> a.getAddress().getHostName())
					.collect(Collectors.toList());
		} catch (SocketException e) {
			throw new RuntimeException("Cannot access avaliable interfaces");
		}
	}

	private static <T> Stream<T> enumerationAsStream(Enumeration<T> e) {
		return StreamSupport.stream(
				Spliterators.spliteratorUnknownSize(
						new Iterator<T>() {
							public T next() {
								return e.nextElement();
							}
							public boolean hasNext() {
								return e.hasMoreElements();
							}
						},
						Spliterator.ORDERED), false);
	}

	public static String guessIp() {
		String ip = null;
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

				ip = addr.getHostAddress();

				System.out.println("Using IP " + addr.getHostAddress());
//				System.setProperty("java.rmi.server.hostname",
//						addr.getHostAddress());

			}
		} catch (SocketException e) {
			throw new RuntimeException(e);
		}

		return ip;
	}
}
