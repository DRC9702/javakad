import org.junit.Test;
import static org.junit.Assert.*;

import java.util.BitSet;

public class UtilitiesTest {

    @Test
    public void testGetRandomBitSet(){
        for(int i=0; i<100; i++) {
            BitSet bitset = Utilities.getRandomBitSet(160);
            //BitSet may use more bits, but the highest set bit will have index <=159
            assertTrue(bitset.length() <= 160);
        }
    }

    @Test
    public void testDifferentGetRandomBitSet(){
        BitSet b1 = Utilities.getRandomBitSet(160);
        BitSet b2 = Utilities.getRandomBitSet(160);
        assertFalse(b1.equals(b2));
    }
}
