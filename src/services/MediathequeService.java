package services;

import java.net.Socket;

import mediatheque.Mediatheque;
import server.ServerService;

public abstract class MediathequeService extends ServerService {
	
	private static Mediatheque mediatheque;
	public MediathequeService(Socket socket) {
		super(socket);
	}
	
	public static void setMediatheque(Mediatheque m) {
		mediatheque = m;
	}
	
	
	public static Mediatheque mediatheque() {
		return mediatheque;
	}


}
