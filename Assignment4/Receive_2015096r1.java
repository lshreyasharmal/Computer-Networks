import java.net.DatagramPacket;
import java.net.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.util.*;

public class Receive_2015096r1 {
    public static final double PROBABILITY = 0.1;
    static int recwnd = 5;
    public static void main(String args[]) throws Exception {
        System.out.println("Receiver is Ready");
        DatagramSocket ReceiveSocket = new DatagramSocket(9016);
        byte[] receiveData = new byte[1024];
        byte[] sendData = new byte[1024];
        Vector<datapacket_2015096r1> window = new Vector<datapacket_2015096r1>();
        boolean finsihs = false;
        int waitfor = 0;

        boolean isprocesscomplete = false;
        while (!(isprocesscomplete)) {
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            ReceiveSocket.receive(receivePacket);

            byte[] data = receivePacket.getData();
            ByteArrayInputStream b1 = new ByteArrayInputStream(data);
            ObjectInputStream o1 = new ObjectInputStream(b1);
            Object sck =   o1.readObject();
            datapacket_2015096r1 dp = (datapacket_2015096r1) sck;
            String sq1 = Integer.toString(dp.getSeqnum());
            String msg = new String(dp.getData());
            System.out.println("Received Seq Num = " + sq1 + " Message = " + msg);
            int seqnum = dp.getSeqnum();
            if (seqnum == waitfor && dp.getislastpacket()) {
                isprocesscomplete = true;
                window.add(dp);
                waitfor++;
                System.out.println("Last Packet Received");

            } else {
                if (waitfor == seqnum) {
                    window.add(dp);
                    waitfor++;
                    System.out.println("Packet added to buffer");
                } else {
                    System.out.println("Packet discarded because of disorder");
                }}

            ackpacket_2015096r1 ack = new ackpacket_2015096r1(waitfor);
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            ObjectOutputStream o = new ObjectOutputStream(b);
            o.writeObject(ack);
            byte[] byetarrat = b.toByteArray();
                DatagramPacket ackpk = new DatagramPacket(byetarrat, byetarrat.length, receivePacket.getAddress(), receivePacket.getPort());

                if (Math.random()>PROBABILITY) {
                    ReceiveSocket.send(ackpk);
                    System.out.println("Sending ACK " + waitfor);
                } else {
                    System.out.println("Packet Loss " + waitfor);
                }
                System.out.println();
            }


        System.out.println("Transmission Over");

        for (int i = 0; i < window.size(); i++) {
            String data = new String(window.get(i).getData());
            System.out.print(data);
        }

    }
}
