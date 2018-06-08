import org.junit.Test;


public class NodeTest {

    @Test
    public void testConstructor() {
        Node node1 = new Node();
        System.out.println(node1.getNodeID().toString());
        Node node2 = new Node("c508312e44947e3b4b12969501731626d98b0d29");
        System.out.println(node2.getNodeID().toString());
    }
}
