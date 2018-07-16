import java.net.*;
import java.lang.*;
public class Server1_2015096 {

    public static void main(String[] args) throws Exception
    {
        Socket socket = null;
        ServerClientThread_2015096 sct=null;

        ServerSocket server = new ServerSocket(1609 );
        int clientnum = 0;

        System.out.println("Hi! I am Server...");

        while(true)
        {
           socket = server.accept();
           sct = new ServerClientThread_2015096(socket, ++clientnum);
           System.out.println("Client " + clientnum + " Connected");
           sct.start();
        }
    }

}
