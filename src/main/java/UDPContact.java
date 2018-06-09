import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;

public class UDPContact implements Contact {

    private String contactType;
    private BitSet nodeID;
    private String host;
    private int port;
    private JSONObject json;

    public static final String DEFAULT_HOST = "localhost";
    public static final int DEFAULT_PORT = 9702;

    public UDPContact(BitSet nodeID){
        this(nodeID,DEFAULT_HOST,DEFAULT_PORT);
    }

    public UDPContact(BitSet nodeID, String host, int port){
        this.contactType = "UDP";
        this.nodeID = nodeID;
        this.host = host;
        this.port = port;

        this.json = new JSONObject();
        json.put("contactType", this.contactType);

        json.put("nodeID", Utilities.bitSetToString(this.nodeID));
        json.put("host", this.host);
        json.put("port", this.port);
    }

    @Override
    public String getContactType() {
        return contactType;
    }

    @Override
    public BitSet getNodeID() {
        return nodeID;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    @Override
    public String serialize() {
        return json.toJSONString();
    }

    public static Contact deSerialize(String str) {
        JSONParser jsonParser = new JSONParser();
        try {
            JSONObject json = (JSONObject) jsonParser.parse(str);
            if(((String)json.get("contactType")).equals("UDP")) {
                String hex = (String) json.get("nodeID");
                BitSet nodeID = Utilities.hexToBitSet(hex);
                String host = (String) json.get("host");
                int port = ((Long)json.get("port")).intValue();
                return new UDPContact(nodeID, (String) host, port);
            }
            else
                throw new IllegalArgumentException("Invalid String Passed In: " + str);
        } catch(ParseException pe){
            System.err.println("position: " + pe.getPosition());
            System.err.println(pe);
            return null;
        }

    }
}
