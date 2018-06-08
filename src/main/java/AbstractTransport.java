import java.util.ArrayList;
import java.util.BitSet;

public abstract class AbstractTransport implements Transport{
    //ToDo: Figure out input and output streams in java. This is hard. :/
    //ToDo: Maybe do parallelism?

    ArrayList<StreamListener> streamListeners = new ArrayList<>();

    public void addStreamListener(StreamListener streamListener){
        this.streamListeners.add(streamListener);
    }

    public abstract void write(Contact contact, byte[] encoding);

    public abstract void read();

    public abstract void listen();
}
