import org.json.simple.JSONObject;

import java.util.BitSet;

public class Node extends AbstractNode {

    Bucket[] buckets = new Bucket[160];
    Transport transport;

    public Node(){
        super();
        initializeNode();
    }

    public Node(String nodeID) {
        super(nodeID);
        initializeNode();
    }

    public Node(Contact contact) {
        super(contact);
        initializeNode();
    }

    public Node(String nodeID, int alpha, int B, int k) {
        super(nodeID, alpha, B, k);
        initializeNode();
    }

    private void initializeNode(){
        initializeBuckets();
        //ToDo: Put in options for other types of transport (default: UDP).
        this.transport = new UDPTransport(this.getContact());
        this.transport.addStreamListener(this.BucketUpdater);
    }

    private void initializeBuckets(){
        for(int i=0; i<buckets.length; i++){
            buckets[i] = new Bucket(this.k);
        }
    }

    public Bucket[] getBuckets() {
        return buckets;
    }

    private StreamListener BucketUpdater = new StreamListener() {
        @Override
        public void onDataEvent(byte[] buffer) {
            JSONObject jsonObject = Utilities.bufferToJSONObject(buffer);
            Contact otherContact = UDPContact.deSerialize((String)jsonObject.get("contact"));
            BitSet contactNodeID = (BitSet) otherContact.getNodeID();
            int distance = Utilities.xorDistance(getNodeID(),contactNodeID);
            buckets[distance].update(contactNodeID,otherContact);
        }
    };


}
