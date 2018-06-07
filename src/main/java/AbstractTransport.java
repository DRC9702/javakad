import java.util.ArrayList;
import java.util.BitSet;

public abstract class AbstractTransport {
    //ToDo: Figure out input and output streams in java. This is hard. :/
    //ToDo: Maybe do parallelism?

    ArrayList<StreamListener> streamListeners = new ArrayList<>();

    public void addStreamListener(StreamListener streamListener){
        this.streamListeners.add(streamListener);
    }

    abstract void write(BitSet targetID, byte[] encoding);

    abstract void read();

    abstract void listen();
}
