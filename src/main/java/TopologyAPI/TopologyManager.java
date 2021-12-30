package TopologyAPI;

import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public final class TopologyManager {
    private TopologyManager () {

    }

    public static void readJSON (String filePath) throws IOException {
        JsonObject jsonObject = JsonHelperClass.createJsonObject(filePath);
        String topologyID = JsonHelperClass.getTopologyID(jsonObject);
        ArrayList<Device> devices = JsonHelperClass.getTopologyDevices(jsonObject);
        DataBaseSimulator.topologies.add(new Topology(topologyID, devices));
    }

    public static void writeJSON (String topologyID, String filePath) throws IOException {
        HashMap<String, Object> jsonMap = JsonHelperClass.convertTopologyToMap(getTopology(topologyID));
        JsonHelperClass.writeMapToJSON(jsonMap, filePath);
    }

    public static ArrayList<Topology> queryTopologies () {
        return DataBaseSimulator.topologies;
    }

    public static ArrayList<Device> queryDevices (String topologyID) {
        Topology topology = getTopology(topologyID);
        assert topology != null;
        return new ArrayList<>(topology.getComponents());
    }

    public static ArrayList<Device> queryDevicesWithNetListNode (String topologyID, String netListNodeID) {
        ArrayList<Device> devices = new ArrayList<>();
        Topology topology = getTopology(topologyID);
        assert topology != null;
        for (Device device : topology.getComponents()) {
            if (device.getNetList().containsValue(netListNodeID)) {
                devices.add(device);
            }
        }
        return devices;
    }

    public static void deleteTopology (String topologyID) {
        Topology topology = getTopology(topologyID);
        assert topology != null;
        DataBaseSimulator.topologies.remove(topology);
    }

    private static Topology getTopology (String topologyID) {
        Topology topology = null;
        for (Topology topology1 : DataBaseSimulator.topologies) {
            if (Objects.equals(topology1.getID(), topologyID)) {
                topology = topology1;
                break;
            }
        }
        return topology;
    }
}
