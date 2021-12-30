package TopologyAPI;

import java.util.ArrayList;

public class Topology {
    String ID;
    ArrayList<Device> components;

    public Topology () {

    }

    public Topology(String ID, ArrayList<Device> components) {
        this.ID = ID;
        this.components = components;
    }


    public void setID(String ID) {
        this.ID = ID;
    }

    public void setComponents(ArrayList<Device> components) {
        this.components = components;
    }


    public String getID() {
        return ID;
    }

    public ArrayList<Device> getComponents() {
        return new ArrayList<>(components);
    }
}
