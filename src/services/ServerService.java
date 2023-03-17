package services;

import java.net.Socket;

import app.Mediatheque;

public abstract class ServerService implements Runnable{
	private Socket socket;
	Mediatheque mediatheque;
	
	public ServerService(Socket socket, Mediatheque m) {
		this.socket =socket;
		this.mediatheque = m;
	}
	
	protected Socket socket() {
		return this.socket;
	}
	
	protected Mediatheque mediatheque(){
		return this.mediatheque;
	}
	
}
