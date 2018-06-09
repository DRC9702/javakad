import org.junit.Test;
import static org.junit.Assert.*;

import java.util.BitSet;

public class UDPContactTest {

    @Test
    public void testConstructor() {
        BitSet nodeID = Utilities.getRandomBitSet(160);
        UDPContact udpContact = new UDPContact(nodeID,"localhost",9732);
    }

    @Test
    public void testContactType() {
        BitSet nodeID = Utilities.getRandomBitSet(160);
        UDPContact udpContact = new UDPContact(nodeID,"localhost",9732);
        assertEquals(udpContact.getContactType(),"UDP");
    }
}
