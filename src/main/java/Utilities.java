import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.BitSet;
import java.util.Comparator;
import java.util.Formatter;

public class Utilities {

    static BitSet getRandomBitSet(int numBits){
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[numBits/8];
        random.nextBytes(bytes);
        return BitSet.valueOf(bytes);
    }

    static String bitSetToString(BitSet bitSet) {
        byte[] bytes = bitSet.toByteArray();
        Formatter formatter = new Formatter();
        for (byte b : bytes) {
            formatter.format("%02x", b);
        }
        return formatter.toString();
    }

    static int xorDistance(BitSet a1, BitSet a2){
        BitSet b1 = (BitSet) a1.clone();
        BitSet b2 = (BitSet) a2.clone();
        b1.xor(b2);
        return b1.cardinality();
    }

    static Comparator<BitSet> xorComparator(BitSet key){
        return new Comparator<BitSet>() {
            @Override
            public int compare(BitSet o1, BitSet o2) {
                int d1 = xorDistance(o1,key);
                int d2 = xorDistance(o2,key);
                return d1-d2;
            }
        };
    }
}
