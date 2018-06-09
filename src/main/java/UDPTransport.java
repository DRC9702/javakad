import java.io.IOException;
import java.net.*;
import java.util.Base64;
import java.util.BitSet;

public class UDPTransport extends AbstractTransport{

    DatagramSocket datagramSocket;
    final static int MAX_RECV_LENGTH = 8000;

    public UDPTransport(Contact contact){
        if(!contact.getContactType().equals("UDP") || !(contact instanceof UDPContact))
            throw new IllegalArgumentException("Invalid Contact Type Fed into UDPTransport:" + contact.getContactType());
        UDPContact udpContact = (UDPContact) contact;
        String host = udpContact.getHost();
        int port = udpContact.getPort();

        try {
            InetAddress inetAddress = InetAddress.getByName(host);
            this.datagramSocket = new DatagramSocket(port,inetAddress);
        } catch (UnknownHostException | SocketException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(Contact contact, byte[] buffer) {
        if(!contact.getContactType().equals("UDP") || !(contact instanceof UDPContact))
            throw new IllegalArgumentException("Invalid Contact Type Fed into UDPTransport:" + contact.getContactType());
        UDPContact udpContact = (UDPContact) contact;

        new Thread(new Runnable() {
            public void run()
            {
                try {
                    InetAddress inetAddress = InetAddress.getByName(udpContact.getHost());
                    DatagramPacket datagramPacket = new DatagramPacket(buffer,buffer.length, inetAddress, udpContact.getPort());
                    datagramSocket.send(datagramPacket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void read() {
        DatagramPacket datagramPacket = new DatagramPacket(new byte[MAX_RECV_LENGTH],MAX_RECV_LENGTH);
        try {
            datagramSocket.receive(datagramPacket);
            byte[] buffer = new byte[datagramPacket.getLength()];
            System.arraycopy(datagramPacket.getData(), 0, buffer, 0, datagramPacket.getLength());

            new Thread(new Runnable() {
                public void run()
                {
                    for(StreamListener streamListener : streamListeners){
                        streamListener.onDataEvent(buffer);
                    }
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void listen() {

    }
}
