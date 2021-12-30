package TopologyAPI;
import java.util.HashMap;

public class Device {
    private String type;
    private String ID;
    private Characteristic characteristics;
    private HashMap<String, String> netList;


    public Device () {

    }

    public Device (String type, String ID, Characteristic characteristics, HashMap<String, String> netList) {
        this.type = type;
        this.ID = ID;
        this.characteristics = characteristics;
        this.netList = netList;
    }

    public void setType (String type) {
        this.type = type;
    }

    public void setID (String ID) {
        this.ID = ID;
    }

    public void setCharacteristics (Characteristic characteristics) {
        this.characteristics = characteristics;
    }

    public void setNetList (HashMap<String, String> netList) {
        this.netList = netList;
    }


    public String getType () {
        return type;
    }

    public String getID () {
        return ID;
    }

    public Characteristic getCharacteristics () {
        return characteristics;
    }

    public HashMap<String, String> getNetList () {
        return new HashMap<>(netList);
    }
}
