import java.io.IOException;
import java.net.*;
import java.util.Base64;
import java.util.BitSet;

public class UDPTransport extends AbstractTransport{

    DatagramSocket datagramSocket;
    final static int MAX_RECV_LENGTH = 256;

    public UDPTransport(String host, int port){
        try {
            InetAddress inetAddress = InetAddress.getByName(host);
            this.datagramSocket = new DatagramSocket(port,inetAddress);
        } catch (UnknownHostException | SocketException e) {
            e.printStackTrace();
        }
    }

    @Override
    void write(Contact contact, byte[] buffer) {
        if(!contact.getContactType().equals("UDP") || !(contact instanceof UDPContact))
            throw new IllegalArgumentException("Invalid Contact Type Fed into UDPTransport:" + contact.getContactType());
        UDPContact udpContact = (UDPContact) contact;

        new Thread(new Runnable() {
            public void run()
            {
                System.out.println("Sending Pre-Encoded: " + buffer.length);
                byte[] encodedBuffer = Base64.getEncoder().encode(buffer);
                System.out.println("Sending Encoded: " + buffer.length);
                try {
                    InetAddress inetAddress = InetAddress.getByName(udpContact.getHost());
                    DatagramPacket datagramPacket = new DatagramPacket(encodedBuffer,encodedBuffer.length, inetAddress, udpContact.getPort());
                    datagramSocket.send(datagramPacket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    void read() {
        DatagramPacket datagramPacket = new DatagramPacket(new byte[MAX_RECV_LENGTH],MAX_RECV_LENGTH);
        try {
            datagramSocket.receive(datagramPacket);
            byte[] encodedBuffer = new byte[datagramPacket.getLength()];
            System.arraycopy(datagramPacket.getData(), 0, encodedBuffer, 0, datagramPacket.getLength());
            System.out.println("Receiving Pre-Decoded: " + encodedBuffer.length);
            byte[] decodedBuffer = Base64.getDecoder().decode(encodedBuffer);
            System.out.println("Receiving Decoded: " + decodedBuffer.length);

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
