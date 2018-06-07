import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.Base64;
import java.util.BitSet;

public class UDPTransport extends AbstractTransport{

    DatagramSocket datagramSocket;

    public UDPTransport(String host, int port) throws SocketException {
        InetSocketAddress inetSocketAddress = InetSocketAddress.createUnresolved(host,port);
        this.datagramSocket = new DatagramSocket(inetSocketAddress);

    }

    @Override
    void write(BitSet targetID, byte[] buffer) {
        new Thread(new Runnable() {
            public void run()
            {
                System.out.println("Sending Pre-Encoded: " + buffer.length);
                byte[] encodedBuffer = Base64.getEncoder().encode(buffer);
                System.out.println("Sending Encoded: " + buffer.length);
                DatagramPacket datagramPacket = new DatagramPacket(encodedBuffer,encodedBuffer.length);
                try {
                    datagramSocket.send(datagramPacket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    void read() {
        byte[] encodedBuffer = new byte[256];
        DatagramPacket datagramPacket = new DatagramPacket(encodedBuffer,encodedBuffer.length);
        try {
            datagramSocket.receive(datagramPacket);
            encodedBuffer = datagramPacket.getData();
            System.out.println("Receiving Pre-Decoded: " + encodedBuffer.length);
            byte[] decodedBuffer = Base64.getDecoder().decode(encodedBuffer);
            System.out.println("Receiving Decoded: " + decodedBuffer.length);
//            String receivedMsg = new String(decodedBuffer, 0, decodedBuffer.length);
//            System.out.print("Message Received:" + receivedMsg);

            new Thread(new Runnable() {
                public void run()
                {
                    for(StreamListener streamListener : streamListeners){
                        streamListener.onDataEvent(decodedBuffer);
                    }
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    void listen() {

    }
}
