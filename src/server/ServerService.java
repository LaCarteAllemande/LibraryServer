package server;

import java.net.Socket;


public abstract class ServerService implements Runnable{
	private Socket socket;
	
	public ServerService(Socket socket) {
		this.socket =socket;
	}
	
	protected Socket socket() {
		return this.socket;
	}	
}
