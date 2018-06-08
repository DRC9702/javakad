import org.junit.Test;

import java.util.BitSet;

import static org.junit.Assert.*;

public class UDPTransportTest {
    @Test public void testConstructor() {
        UDPTransport udpTransport = new UDPTransport("localhost",9702);
    }

    @Test public void testWrite() {
        UDPTransport udp1 = new UDPTransport("localhost",9703);

        UDPContact contact2 = new UDPContact(Utilities.getRandomBitSet(160),"localhost",9704);
        UDPTransport udp2 = new UDPTransport(contact2.getHost(),contact2.getPort());

        udp1.write(contact2,"Hello".getBytes());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test public void testRead() {
        UDPTransport udp1 = new UDPTransport("localhost",9705);

        UDPContact contact2 = new UDPContact(Utilities.getRandomBitSet(160),"localhost",9706);
        UDPTransport udp2 = new UDPTransport(contact2.getHost(),contact2.getPort());

        final boolean[] gotMsg = {false};

        udp2.addStreamListener(new StreamListener() {
            @Override
            public void onDataEvent(byte[] buffer) {
                String recv = new String(buffer,0,buffer.length);
                System.out.println("Received Message: " + recv);
                assertEquals(recv,"Hello");
                gotMsg[0] = true;
            }
        });

        udp1.write(contact2,"Hello".getBytes());
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
