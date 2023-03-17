package threads;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import app.Mediatheque;
import services.ServerService;



public class LibraryRunnable implements Runnable {
	private ServerSocket serverSocket;
	Mediatheque mediatheque;
	Class<? extends ServerService> serviceClass;
	
	public LibraryRunnable(int port, Mediatheque m, Class<? extends ServerService> service) throws IOException{
		serverSocket = new ServerSocket(port);
		this.mediatheque = m;
		this.serviceClass = service;
	}
	
	
	@Override
	public void run() {
        while(true) {
            try {
                Socket socket = serverSocket.accept();
                
				try {
					Constructor<? extends ServerService> constructor;
					constructor = serviceClass.getConstructor(Socket.class, Mediatheque.class);
					ServerService service = constructor.newInstance(socket, mediatheque);
					new Thread(service).start();
					
				} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
					System.out.println("Probleme avec la classe de service");
					socket.close();
				} 

                
            } catch (IOException e) {
                System.out.println("Erreur durant la connexion");
            } 	
            
        }
	}
	
	public void stop() {
	    try {
	        serverSocket.close();
	    } catch (IOException e) {
	    }
    }

}
