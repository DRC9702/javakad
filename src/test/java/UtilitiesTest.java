import org.json.simple.JSONObject;
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
        for(int i=0; i<100; i++) {
            BitSet b1 = Utilities.getRandomBitSet(160);
            BitSet b2 = Utilities.getRandomBitSet(160);
            assertFalse(b1.equals(b2));
        }
    }

    @Test
    public void testJSONBufferConversion(){
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("nodeID",Utilities.getRandomBitSet(160).toString());
        byte[] buffer = Utilities.jsonObjectToBuffer(jsonObject1);
        JSONObject jsonObject2 = Utilities.bufferToJSONObject(buffer);
        assertTrue(jsonObject1.equals(jsonObject2));
    }
}
