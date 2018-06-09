import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.ByteArrayOutputStream;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
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

    static BitSet hexToBitSet(String hexString){
        if(hexString.matches("^[a-f0-9]+$") && hexString.length()==40){
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            for(int i = 0; i < hexString.length() - 1; i += 2) {
                String data = hexString.substring(i, i + 2);
                bout.write(Integer.parseInt(data, 16));
            }
            return BitSet.valueOf(bout.toByteArray());
        }
        else{
            throw new IllegalArgumentException("Invalid NodeID hexString:" + hexString);
        }
    }

    static int xorDistance(BitSet a1, BitSet a2){
        BitSet b1 = (BitSet) a1.clone();
        BitSet b2 = (BitSet) a2.clone();
        b1.xor(b2);
        return b2.cardinality();
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

    static byte[] stringToBuffer(String str){
        byte[] encodedBuffer = Base64.getEncoder().encode(str.getBytes());
        return encodedBuffer;
    }

    static byte[] jsonObjectToBuffer(JSONObject jsonObject){
        byte[] encodedBuffer = Base64.getEncoder().encode(jsonObject.toJSONString().getBytes());
        return encodedBuffer;
    }

    static String bufferToString(byte[] buffer){
        byte[] decodedBuffer = Base64.getDecoder().decode(buffer);
        String str = new String(decodedBuffer,0,decodedBuffer.length);
        return str;
    }

    static JSONObject bufferToJSONObject(byte[] buffer){
        byte[] decodedBuffer = Base64.getDecoder().decode(buffer);
        String str = new String(decodedBuffer,0,decodedBuffer.length);
        JSONParser jsonParser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(str);
            return jsonObject;
        } catch (ParseException e) {
            System.err.println("PARSING ERROR: " + str);
            e.printStackTrace();
        }
        return null;
    }
}
