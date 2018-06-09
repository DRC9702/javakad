import org.json.simple.JSONObject;

import java.util.BitSet;
import java.util.HashMap;
import java.util.List;

public interface Contact {

    String getContactType();

    String serialize();

    static Contact deSerialize(String str){
        throw new RuntimeException("Unimplemented deserialize method.");
    };

    String toString();

    BitSet getNodeID();
}
