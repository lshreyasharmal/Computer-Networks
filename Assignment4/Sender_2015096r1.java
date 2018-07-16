import java.util.*;
import java.net.*;
import java.io.*;
public class Sender_2015096r1 {
    public static final double PROBABILITY = 0.1;
    static int num = 0;
    static int windowsize = 1;
    static int tmer = 1000;

    static int datasize = 3;
    public static void main(String[] args) throws Exception
    {
        System.out.println("Sender is Ready");
        int waitfor = 0;
        BufferedReader buff = new BufferedReader(new InputStreamReader(System.in));
        DatagramSocket SendingSocket = new DatagramSocket();
        InetAddress IPadd = InetAddress.getByName("localhost");
        String x = buff.readLine();
        int datasize  = 10;
        int lastseqnum = (int)Math.ceil((x.getBytes().length)*1.0/datasize);
//        break temp into segments
        Vector<datapacket_2015096r1> packets = new Vector<datapacket_2015096r1>();
        String temp="";

        for(int i=0;i<x.length();i++){
            if(i!=0 && i%datasize==0){
                byte[] packetdata = temp.getBytes();
                datapacket_2015096r1 dp = new datapacket_2015096r1(num,packetdata,false);
                packets.add(dp);
                num++;
                temp="";
            }
            temp+=x.charAt(i);
        }
        byte[] packetdata = temp.getBytes();
        datapacket_2015096r1 dp = new datapacket_2015096r1(num,packetdata,true);
        packets.add(dp);
        System.out.println(lastseqnum);
        for(int i=0;i<packets.size();i++){
            System.out.println(packets.get(i).getSeqnum() + " " + new String(packets.get(i).getData())+" "+ packets.get(i).getislastpacket());
        }

        int lastsent= 0;
        int start =0;
        int end = 1;
        boolean packetloss = false;
        boolean isssthread = false;
        boolean normal = false;
        int ssthread = -1;
        boolean timeout = false;
        int j = 1;
        int y = 0;
        System.out.println(lastseqnum);
        boolean over = false;
        while(true) {
            if(windowsize>=ssthread && ssthread!=-1)
                isssthread = true;
            int i;
            for(i=lastsent;i<lastseqnum;i++,lastsent++){
                if (i - waitfor >= windowsize) {
                    break;
                }
                datapacket_2015096r1 temp1 = packets.get(i);
                ByteArrayOutputStream b = new ByteArrayOutputStream();
                ObjectOutputStream o = new ObjectOutputStream(b);
                o.writeObject(temp1);
                byte[] byetarrat = b.toByteArray();
                DatagramPacket sendPacket = new DatagramPacket(byetarrat,byetarrat.length, IPadd, 9016);
                System.out.println("Sending packet "+ packets.get(i).getSeqnum());
                if(Math.random()>PROBABILITY) {
                    SendingSocket.send(sendPacket);
                }else{
                    System.out.println("PACKET LOSS " + i);
                }

            }

            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            int d = -1;

            while(d!=lastsent){
                try{
                    SendingSocket.setSoTimeout(tmer);
                    SendingSocket.receive(receivePacket);
                    byte[] data = receivePacket.getData();
                    ByteArrayInputStream b = new ByteArrayInputStream(data);
                    ObjectInputStream o = new ObjectInputStream(b);
                    Object sck =   o.readObject();
                    ackpacket_2015096r1 ack = (ackpacket_2015096r1) sck;
                    System.out.println("From Receiver: ACK " + ack.getPacket());
                    y++;
                    d = ack.getPacket();
                    if(d == waitfor)
                    {
                        System.out.println("Duplicate ACK received");
                        packetloss = true;
                    }
                    if(ack.getPacket()==lastseqnum) {
                        over = true;
                        break;
                    }
                    waitfor = Math.max(waitfor,ack.getPacket());
                }catch (SocketTimeoutException e){
                    System.out.println("TIMEOUT");
                    timeout = true;
                    int ylastsent = waitfor;
                    while(ylastsent < i){
                        datapacket_2015096r1 yu = packets.get(ylastsent);
                        ByteArrayOutputStream b = new ByteArrayOutputStream();
                        ObjectOutputStream o = new ObjectOutputStream(b);
                        o.writeObject(yu);
                        byte[] sd = b.toByteArray();
                        int port = 9016;
                        DatagramPacket sdp = new DatagramPacket(sd,sd.length,IPadd,port);
                        System.out.println("Retransmitting Packet "+ packets.get(ylastsent).getSeqnum());
                        double x1 = Math.random();
                        if(x1>PROBABILITY) {
                            SendingSocket.send(sdp);
                        }else{
                            System.out.println("PACKET FOR RETRANSMISSION LOST " + packets.get(ylastsent).getSeqnum()); }
                        ylastsent+=1; }

                }
            }
            if(over)
                break;
            if(timeout){
                ssthread = windowsize/2;
                windowsize = 1;
                timeout = false;
                packetloss = false;
                continue;
            }
            else if(packetloss){
                windowsize /=2;
                ssthread = windowsize;
                timeout = false;
                continue;
            }
            if(windowsize >= Receive_2015096r1.recwnd)
            {
                windowsize = Receive_2015096r1.recwnd;
                continue;
            }
            else if(packetloss || isssthread)
                windowsize++;
            else
                windowsize*=2;

        }
        System.out.println("DONE");

    }

}
