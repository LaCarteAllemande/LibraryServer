package server;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerRunnable implements Runnable {
	private ServerSocket serverSocket;
	private Class<? extends ServerService> serviceClass;
	
	public ServerRunnable(int port, Class<? extends ServerService> service) throws IOException{
		serverSocket = new ServerSocket(port);
		this.serviceClass = service;
	}
	
	
	@Override
	public void run() {
        while(true) {
            try {
                Socket socket = serverSocket.accept();
                
				try {
					Constructor<? extends ServerService> constructor;
					constructor = serviceClass.getConstructor(Socket.class);
					ServerService service = constructor.newInstance(socket);
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
