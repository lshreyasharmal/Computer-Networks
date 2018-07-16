import java.io.*;
public class ackpacket_2015096r1 implements Serializable{
    private int packetnum;
    public ackpacket_2015096r1(int pack) {
        super();
        this.packetnum = pack;
    }
    public int getPacket() {
        return packetnum;
    }
    void setPacketnum(int pack)
    {
        this.packetnum=pack;
    }
}
