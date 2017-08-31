import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class ServerClientThread_2015096 extends Thread {

    DataOutputStream dos=null;
    DataInputStream din=null;

    private static Socket socket;
    private static int clientnum;


    ServerClientThread_2015096(Socket socket_,int clientnum_)
    {
        socket = socket_;
        clientnum =clientnum_;
    }

    public static String Reverse(String line)
    {
        String res = "";
        String[] Line = line.split("\\s");
        for(int i=0;i<Line.length;i++)
            res = res + (Line[Line.length-1-i]) + " ";
        return res;
    }

    public void run() {
        try{
            dos = new DataOutputStream(socket.getOutputStream());
            din = new DataInputStream(socket.getInputStream());

            String clientmsg="";
            String servermsg="";

            while (!clientmsg.equals("end"))
            {
                clientmsg=din.readUTF();
                System.out.println("Received from Client "+clientnum+" : "+clientmsg );
                String res = Reverse(clientmsg);
                servermsg = "Received from Server: " + res;
                dos.writeUTF(servermsg);
                dos.flush();
            }
            dos.close();
            din.close();
            socket.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
        finally
        {
            System.out.println("Client "+clientnum+" disconnected   ");
        }
    }
}
