public interface Transport {

    void addStreamListener(StreamListener streamListener);

    void write(Contact contact, byte[] encoding);

    void read();

    void listen();
}
