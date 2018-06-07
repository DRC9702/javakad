import org.json.simple.JSONObject;

import java.util.BitSet;
import java.util.HashMap;
import java.util.List;

public interface Contact {

    String serialize();

//    Contact deSerialize(String str);

    String toString();

    BitSet getNodeID();
}
