import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client1_2015096r1 {
    static int portnum=1699;

    public static void main(String[] args) throws IOException {
        Scanner s = new Scanner(System.in);
      //  InetAddress ip = InetAddress.getByName("localhost");
        Socket sock = new Socket("127.0.0.1", portnum);

        DataInputStream dis = new DataInputStream(sock.getInputStream());
        DataOutputStream dos = new DataOutputStream(sock.getOutputStream());

        Thread send = new Thread(() -> {
            while(true)
            {
                String message = s.nextLine();
                try {
                    dos.writeUTF(message);
                } catch (IOException e) {
                    break;
                }


            }
        });

        Thread read = new Thread(() -> {
            while(true)
            {
                String message;
                try {
                    message = dis.readUTF();
                    System.out.println(message);
                } catch (IOException e) {
                   break;
                }

            }
        });

        send.start();
        read.start();

    }
}
