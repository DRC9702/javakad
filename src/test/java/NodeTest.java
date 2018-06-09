import org.junit.Test;
import static org.junit.Assert.*;

import org.json.simple.JSONObject;

public class NodeTest {

    @Test
    public void testConstructor(){
        Contact contact1 = new UDPContact(Utilities.getRandomBitSet(160),"localhost",9703);
        Node node1 = new Node(contact1);
//        System.out.println(node1.getNodeID().toString());
        Node node2 = new Node("c508312e44947e3b4b12969501731626d98b0d29");
//        System.out.println(node2.getNodeID().toString());
        assertTrue(true);
    }

    @Test
    public void testEmptyBuckets(){
        Contact contact = new UDPContact(Utilities.getRandomBitSet(160),"localhost",9704);
        Node node = new Node(contact);
        int totalBucketSize = 0;
        for(int i=0; i<node.getBuckets().length; i++)
            totalBucketSize += node.getBuckets()[i].getSize();
        assertEquals(totalBucketSize,0);
    }

    @Test
    public void testUpdateBucket(){

        UDPContact contact1 = new UDPContact(Utilities.getRandomBitSet(160),"localhost",9705);
        UDPContact contact2 = new UDPContact(Utilities.getRandomBitSet(160),"localhost",9706);


        Node node1 = new Node(contact1);
        Node node2 = new Node(contact2);

        assertFalse(contact1.getNodeID().equals(contact2.getNodeID()));

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("contact", node2.getContact().serialize());
        byte[] buffer = Utilities.jsonObjectToBuffer(jsonObject);
        node1.transport.write(node2.getContact(),buffer);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        node2.transport.read();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int totalBucketSize = 0;
        for(int i=0; i<node2.getBuckets().length; i++)
            totalBucketSize += node2.getBuckets()[i].getSize();

        assertEquals(totalBucketSize,1);

    }
}
