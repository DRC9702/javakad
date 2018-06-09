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

    final protected int tExpire =86410;
    final protected int tRefresh = 3600;
    final protected int tReplicate = 3600;
    final protected int tRepublish = 86400;

    private Contact contact;

    public AbstractNode(){
        this.setContact(new UDPContact(Utilities.getRandomBitSet(this.B)));
    }

    public AbstractNode(Contact contact){
        this.setContact(contact);
    }

    public AbstractNode(String nodeID){
        this.setContact(new UDPContact(Utilities.hexToBitSet(nodeID)));
    }

    public AbstractNode(String nodeID, int alpha, int B, int k){
        this.setContact(new UDPContact(Utilities.hexToBitSet(nodeID)));
        this.alpha = alpha;
        this.B = B;
        this.k = k;
    }

    public BitSet getNodeID() {
        return this.getContact().getNodeID();
    }

    private void setContact(Contact contact){
        this.contact = contact;
    }

    public Contact getContact() {
        return this.contact;
    }
}
