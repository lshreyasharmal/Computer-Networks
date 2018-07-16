import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.*;

public class ServerClientThread_2015096r1 implements Runnable{

    Scanner s = new Scanner(System.in);
    private String name;
    Socket sock;
    boolean islogenin;
    final DataOutputStream dos;
    final DataInputStream din;


    ServerClientThread_2015096r1(Socket socket,String name,DataInputStream din,DataOutputStream dos)
    {
        this.sock = socket;
        this.name = name;
        this.dos = dos;
        this.din = din;
        this.islogenin = true;
    }

    public void run() {
        String clientmsg="";
        while(true)
        {
            try{
                clientmsg = din.readUTF();
                System.out.println(clientmsg);
                if(clientmsg.equals("end"))
                {
                    this.dos.writeUTF("goodbye "+ name);
                    this.islogenin=false;
                    try {
                        this.sock.close();
                        this.dos.close();
                        this.din.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                if(clientmsg.equals("Server: List All"))
                {
                    this.dos.writeUTF("connected clients : ");
                    for(ServerClientThread_2015096r1 friends : Server1_2015096r1.clients)
                    {
                        if(friends.islogenin)
                            this.dos.writeUTF(friends.name+ " ");
                    }
                    continue;
                }

                StringTokenizer st = new StringTokenizer(clientmsg,":");
                String receiver = st.nextToken();
                String msg = st.nextToken();

                if(receiver.equals("All"))
                {
                    for(ServerClientThread_2015096r1 friends : Server1_2015096r1.clients)
                    {
                        if(friends.name.equals(this.name))
                            continue;
                        if(friends.islogenin)
                        {
                            friends.dos.writeUTF(this.name+" :"+msg);
                        }
                        else
                        {
                            this.dos.writeUTF(friends.name+" Disconnected");
                        }

                    }
                    continue;
                }

                StringTokenizer st2 = new StringTokenizer(receiver," ");
                String r = st2.nextToken();
                String receiver1 = st2.nextToken();
                StringTokenizer st1 = new StringTokenizer(receiver1,",");
                ArrayList<String> receivers = new ArrayList<String>();
                while(st1.hasMoreTokens())
                    receivers.add(st1.nextToken());
               // System.out.println(receivers);
                for(String nam : receivers) {
                    String y = "Client "+nam;
                    int check=0;
                    for(ServerClientThread_2015096r1 friends : Server1_2015096r1.clients)
                    {

                        if(y.equals(friends.name))
                        {
                            check=1;
                            if(friends.islogenin)
                            {
                                friends.dos.writeUTF(this.name+" :"+msg);
                                break;
                            }
                            else
                            {
                                this.dos.writeUTF(y+" is Disconnected");
                            }

                        }
                    }
                    if(check==0)
                    {
                        //no client y exist
                        this.dos.writeUTF(y+ " Does not exist");
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            System.out.println("s");
            this.sock.close();
            this.dos.close();
            this.din.close();
        } catch (IOException e) {

        }finally
        {
            System.out.println(name + " disconnected");
        }
    }
}
