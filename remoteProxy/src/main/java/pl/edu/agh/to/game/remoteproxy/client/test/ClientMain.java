package pl.edu.agh.to.game.remoteproxy.client.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import pl.edu.agh.to.game.remoteproxy.client.ClientType;
import pl.edu.agh.to.game.remoteproxy.client.RPClient;

public class ClientMain {
	public static void main(String[] args) throws IOException {
		TestHandler handler = new TestHandler();

		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("Choose client type:");
		System.out.println("[o] observer");
		System.out.println("[c] controller");

		ClientType type;
		String cmd = bufferedReader.readLine();
		
		if(cmd.startsWith("o")) {
			type = ClientType.OBSERVER;
		} else if(cmd.startsWith("c")) {
			type = ClientType.CONTROLLER;
		} else {
			System.out.println("Bad client type, closing program");
			return;
		}

		RPClient rp = new RPClient(handler, type);
		
		while(true) {
			System.out.println("\nClientConsole [? for help] > : ");
			cmd = bufferedReader.readLine();
			
			if(cmd.startsWith("?")) {
				System.out.println("'?'           - print this help");
				System.out.println("'q'           - close program");
			} else if(cmd.startsWith("q")) {
				break;
			}
		}
		
	}
}
