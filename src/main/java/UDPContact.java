import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.BitSet;
import java.util.HashMap;
import java.util.List;

public class UDPContact implements Contact {

    private String contactType;
    private BitSet nodeID;
    private String ip;
    private int port;
    private JSONObject json;

    public UDPContact(BitSet nodeID, String ip, int port){
        this.contactType = "UDP";
        this.nodeID = nodeID;
        this.ip = ip;
        this.port = port;

        this.json = new JSONObject();
        json.put("contactType", this.contactType);
        json.put("nodeID", this.nodeID);
        json.put("ip", this.ip);
        json.put("port", this.port);
    }

    @Override
    public BitSet getNodeID() {
        return null;
    }

    public String getIp() {
        return ip;
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
            if(((String)json.get("contactType")).equals("UDP"))
                return new UDPContact((BitSet)json.get("nodeID"),(String)json.get("ip"),(int)json.get("port"));
            else
                throw new IllegalArgumentException("Invalid String Passed In: " + str);
        } catch(ParseException pe){
            System.err.println("position: " + pe.getPosition());
            System.err.println(pe);
            return null;
        }

    }
}
