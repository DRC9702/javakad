import java.io.ByteArrayOutputStream;
import java.util.BitSet;

/**
 * @author drc9702
 * @apiNote Referenced from: http://xlattice.sourceforge.net/components/protocol/kademlia/specs.html
 */
public abstract class AbstractNode {
    protected int alpha = 3;
    protected int B = 160;
    protected int k = 20;

    protected int tExpire =86410;
    protected int tRefresh = 3600;
    protected int tReplicate = 3600;
    protected int tRepublish = 86400;

    private BitSet nodeID = new BitSet(160);

    public AbstractNode(){
        this.setNodeID(Utilities.getRandomBitSet(this.B));
    }

    public AbstractNode(String nodeID){
        this.setNodeID(nodeID);
    }

    public AbstractNode(String nodeID, int alpha, int B, int k){
        this.setNodeID(nodeID);
        this.alpha = alpha;
        this.B = B;
        this.k = k;
    }

    public AbstractNode(String nodeID, int alpha, int B, int k, int tExpire, int tRefresh, int tReplicate, int tRepublish){
        this.setNodeID(nodeID);
        this.alpha = alpha;
        this.B = B;
        this.k = k;
        this.tExpire = tExpire;
        this.tRefresh = tRefresh;
        this.tReplicate = tReplicate;
        this.tRepublish = tRepublish;
    }

    private void setNodeID(BitSet nodeID){
        this.nodeID = nodeID;
    }

    private void setNodeID(String hexString){
        if(hexString.matches("^[a-f0-9]+$") && hexString.length()==40){
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            for(int i = 0; i < hexString.length() - 1; i += 2) {
                String data = hexString.substring(i, i + 2);
                bout.write(Integer.parseInt(data, 16));
            }
            this.nodeID = BitSet.valueOf(bout.toByteArray());
        }
        else{
            throw new IllegalArgumentException("Invalid NodeID hexString:" + hexString);
        }
    }

    public BitSet getNodeID() {
        return nodeID;
    }
}
