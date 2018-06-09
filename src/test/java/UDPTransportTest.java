import org.junit.Test;

import java.util.BitSet;

import static org.junit.Assert.*;

public class UDPTransportTest {
    @Test public void testConstructor() {
        UDPContact contact = new UDPContact(Utilities.getRandomBitSet(160),"localhost",9722);
        UDPTransport udpTransport = new UDPTransport(contact);
    }

    @Test public void testWrite() {
        UDPContact contact1 = new UDPContact(Utilities.getRandomBitSet(160),"localhost",9723);
        UDPTransport udp1 = new UDPTransport(contact1);

        UDPContact contact2 = new UDPContact(Utilities.getRandomBitSet(160),"localhost",9724);
        UDPTransport udp2 = new UDPTransport(contact2);

        byte[] buffer = Utilities.stringToBuffer("Hello");

        udp1.write(contact2,buffer);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test public void testRead() {
        UDPContact contact1 = new UDPContact(Utilities.getRandomBitSet(160),"localhost",9725);
        UDPTransport udp1 = new UDPTransport(contact1);

        UDPContact contact2 = new UDPContact(Utilities.getRandomBitSet(160),"localhost",9726);
        UDPTransport udp2 = new UDPTransport(contact2);

        final boolean[] gotMsg = {false};

        udp2.addStreamListener(new StreamListener() {
            @Override
            public void onDataEvent(byte[] buffer) {
                String recvMessage = Utilities.bufferToString(buffer);
                System.out.println("Received Message: " + recvMessage);
                assertEquals(recvMessage,"Hello");
                gotMsg[0] = true;
            }
        });

        String sentMessage = "Hello";
        byte[] sentBuffer = Utilities.stringToBuffer(sentMessage);

        udp1.write(contact2,sentBuffer);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        udp2.read();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertTrue(gotMsg[0]);
    }
}
