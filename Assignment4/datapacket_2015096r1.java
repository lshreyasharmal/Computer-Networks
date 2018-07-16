import java.io.*;
import java.util.*;
public class datapacket_2015096r1 implements Serializable{
    private int seqnum;
    private boolean islastpacket;
    private boolean isfirstpacket;
    private byte[] data;

    datapacket_2015096r1(int seqnum,byte[] data,boolean islastpacket){
        super();
        this.data = data;
        this.islastpacket = islastpacket;
        this.isfirstpacket = false;
        this.seqnum = seqnum;
    }

    byte[] getData() {
        return data;
    }
    boolean getislastpacket(){
        return this.islastpacket;
    }
    boolean getisfirstpacket()
    {
        return this.isfirstpacket;
    }
    int getSeqnum(){
        return seqnum;
    }
}
