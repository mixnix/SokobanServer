import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
    /**
     * numer portu na którym działa serwer
     */
    private int port;

    /**
     * metoda rozpoczynająca działanie serwera
     */
    public void run() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("Config\\serwer.txt"));
            port = Integer.parseInt(br.readLine());
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Czekanie na połączenie");
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(new ServerThread(socket)).start();
            }
        }
        catch (IOException e){
            System.out.println("Błąd uruchomienia serwera.");
            System.out.println("Błąd"+e);
        }
    }
}
