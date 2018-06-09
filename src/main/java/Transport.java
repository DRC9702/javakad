public interface Transport {

    void addStreamListener(StreamListener streamListener);

    void write(Contact contact, byte[] buffer);

    void read();

    void listen();
}
