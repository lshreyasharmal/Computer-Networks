import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.*;
import java.lang.*;
import java.util.*;
public class Server1_2015096r1 {
   static LinkedList<ServerClientThread_2015096r1> clients = new LinkedList<ServerClientThread_2015096r1>();
    public static void main(String[] args) throws Exception
    {
        Socket socket ;
        int portNumber = 1699;
        ServerSocket server = new ServerSocket(portNumber);
        int clientnum = 1;

        System.out.println("Hi! I am Server...");

        while(true)
        {
            socket = server.accept();
            System.out.println("Client "+clientnum+" Connected");
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            ServerClientThread_2015096r1 client = new ServerClientThread_2015096r1(socket, "Client "+clientnum++,dis,dos);
            Thread t = new Thread(client);
            clients.add(client);
            t.start();
        }
    }

}
