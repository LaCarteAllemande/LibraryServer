package threads;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.ServerSocket;
import java.net.Socket;
import app.Mediatheque;
import services.LibraryService;



public class LibraryRunnable implements Runnable {
	private ServerSocket serverSocket;
	Mediatheque mediatheque;
	Class<? extends LibraryService> serviceClass;
	
	public LibraryRunnable(int port, Mediatheque m, Class<? extends LibraryService> service) throws IOException{
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
					Constructor<? extends LibraryService> constructor;
					constructor = serviceClass.getConstructor(Socket.class, Mediatheque.class);
					LibraryService service = constructor.newInstance(socket, mediatheque);
					new Thread(service).start();
					
				} catch (Exception e) { //bonne pratique ?
					socket.close();
					System.out.println("Probleme avec la classe de service");
				} 

                
            } catch (IOException e) {
                System.out.println("Erreur durant la connexion");
            }
        }

	}

}
