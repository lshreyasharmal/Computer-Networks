import java.io.*;
import java.net.Socket;
public class Client1_2015096 {

    public static void main(String[] args) throws Exception
    {
        BufferedReader br = null;
        DataOutputStream dos = null;
        DataInputStream din = null;
        Socket socket = null;
        //connecting to server
        socket = new Socket("127.0.0.1", 1609);
        //for data tranfer
        br = new BufferedReader(new InputStreamReader(System.in));
        dos = new DataOutputStream(socket.getOutputStream());
        din = new DataInputStream(socket.getInputStream());
        String servermsg="";
        String clientmsg="";

        while(!clientmsg.equals("end"))
        {
           System.out.println("Client, Send a Message to Server : ");
           //reading client input
           clientmsg = br.readLine();
           dos.writeUTF(clientmsg);
           dos.flush();
           //reading from server
           servermsg=din.readUTF();
           System.out.println(servermsg);
        }
        dos.close();
         socket.close();
    }


}
