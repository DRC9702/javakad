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

    public Node(String nodeID, int alpha, int B, int k) {
        super(nodeID, alpha, B, k);
        initializeNode();
    }

    public Node(String nodeID, int alpha, int B, int k, int tExpire, int tRefresh, int tReplicate, int tRepublish) {
        super(nodeID, alpha, B, k, tExpire, tRefresh, tReplicate, tRepublish);
        initializeNode();
    }

    private void initializeNode(){
        initializeBuckets();
        //ToDo: Put in options for other types of transport (default: UDP).
        this.transport = new UDPTransport("localhost",9702);
    }

    private void initializeBuckets(){
        for(int i=0; i<buckets.length; i++){
            buckets[i] = new Bucket(this.k);
        }
    }
    

}
