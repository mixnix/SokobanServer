import java.io.*;
import java.net.Socket;

/**
 * Created by user_name on 21/05/2017.
 */
public class ServerThread implements Runnable {
    private Socket socket;

    public ServerThread(Socket socket){
        this.socket=socket;
    }

    @Override
    public void run() {
        try {
            while (true) {
                InputStream inputStream = socket.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                OutputStream os = socket.getOutputStream();
                PrintWriter pw = new PrintWriter(os, true);
                String fromClient = br.readLine();
                if(fromClient!=null) {
                    System.out.println("komenda: " + fromClient);
                    String serverMessage = ServerCommands.serverAction(fromClient);
                    if(serverMessage=="CLOSE"){
                        socket.close();
                    }
                    else {
                        pw.println(serverMessage);
                        pw.flush();
                        System.out.println("do klienta: " + serverMessage);
                        if (serverMessage == "CLOSE") {
                            socket.close();
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
