import java.util.*;
// LinkedHashMap isn't enough because indexing is also preferred

public class Bucket {

    private final int k;
    HashMap<BitSet,Contact> nodeToContact = new HashMap<>();
    LinkedList<BitSet> nodeList = new LinkedList<>();

    public Bucket(int k){
        this.k = k;
    }

    public int getSize(){
        return nodeList.size();
    }

    public Contact popHead(){
        if(nodeList.size()>0){
            return nodeToContact.remove(nodeList.removeFirst());
        }
        else {
            throw new IllegalStateException("Bucket is empty!");
        }
    }

    public Contact popTail(){
        if(nodeList.size()>0){
            return nodeToContact.remove(nodeList.removeLast());
        }
        else {
            throw new IllegalStateException("Bucket is empty!");
        }
    }

    public void update(BitSet nodeID, Contact contact){
        if(this.nodeList.contains(nodeID)){
            this.nodeList.addLast(this.nodeList.remove(this.nodeList.indexOf(nodeID)));
        }
        else if(this.nodeList.size() < this.k){
            this.nodeToContact.put(nodeID,contact);
            this.nodeList.addLast(nodeID);
        }
        else{
            //ToDO: Figure out what to do here
            // Ignore?
        }
    }

    public int indexOf(BitSet nodeID){
        return this.nodeList.indexOf(nodeID);
    }

    public TreeMap<BitSet,Contact> getClosestToKey(BitSet key, int size){
        Comparator<BitSet> xorComparator = Utilities.xorComparator(key);
        TreeMap<BitSet,Contact> closest = new TreeMap<>(xorComparator);
        TreeMap<BitSet,Contact> retMap = new TreeMap<>(xorComparator);
        closest.putAll(nodeToContact);
        // ToDo: Kadence filters out original key. Find out why.
        for(BitSet node : closest.keySet()){
            if(size-->0)
                retMap.put(node,closest.get(node));
        }
        return retMap;
    }

}
