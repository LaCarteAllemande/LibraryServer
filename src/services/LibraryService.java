package services;

import java.net.Socket;

import app.Mediatheque;

public abstract class LibraryService implements Runnable{
	private Socket socket;
	Mediatheque mediatheque;
	
	public LibraryService(Socket socket, Mediatheque m) {
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
