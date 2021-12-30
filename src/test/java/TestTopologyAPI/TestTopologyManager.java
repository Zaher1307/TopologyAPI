package TestTopologyAPI;

import TopologyAPI.Device;
import TopologyAPI.JsonHelperClass;
import TopologyAPI.TopologyManager;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.junit.Test;
import org.junit.Assert;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class TestTopologyManager {
    @Test
    public void testDeleteTopology () throws IOException {
        String topology = "src/main/resources/topology.json";
        /* add new topology to the memory */
        TopologyManager.readJSON(topology);

        /* try to delete this topology */
        TopologyManager.deleteTopology("top1");

        /* check if the topology is existing or not  */
        Assert.assertEquals(0, TopologyManager.queryTopologies().size());
    }

    @Test
    public void testReadWriteJSON () throws IOException {
        String expected = "src/main/resources/topology.json";
        String actual = "src/test/resources/write.json";

        /* add new topology to the memory */
        TopologyManager.readJSON(expected);

        /* copy this topology from memory to disk */
        TopologyManager.writeJSON("top1", actual);

        /* add the copy to the memory */
        TopologyManager.readJSON(actual);

        /* check if the two objects in memory is equal and so if then the readJSON and writeJSON is correct */
        JsonElement expectedJsonElement = JsonParser.parseReader(new FileReader(expected));
        JsonElement actualJsonElement = JsonParser.parseReader(new FileReader(actual));
        Assert.assertEquals(actualJsonElement, expectedJsonElement);

        /* try to delete the first topology */
        TopologyManager.deleteTopology("top1");
        /* try to delete the second topology */
        TopologyManager.deleteTopology("top1");
    }

    @Test
    public void testQueryTopologies () throws IOException {
        String expected = "src/main/resources/topology.json";
        String actual = "src/test/resources/write.json";

        /* add new topology to the memory */
        TopologyManager.readJSON(expected);

        /* check if the topology existing or not */
        Assert.assertTrue (TopologyManager.queryTopologies().size() > 0);

        /* try to get this topology by getting the first topology in the memory */
        TopologyManager.writeJSON(TopologyManager.queryTopologies().get(0).getID(), actual);

        /* check if the object in memory is identical to the one which got from the queryTopology method */
        JsonElement expectedJsonElement = JsonParser.parseReader(new FileReader(expected));
        JsonElement actualJsonElement = JsonParser.parseReader(new FileReader(actual));
        Assert.assertEquals(actualJsonElement, expectedJsonElement);

        /* try to delete this topology */
        TopologyManager.deleteTopology("top1");
    }

    @Test
    public void testQueryDevices () throws IOException {
        String topology = "src/main/resources/topology.json";

        /* add new topology to the memory */
        TopologyManager.readJSON(topology);
        String expected = "src/main/resources/devices.json";
        String actual = "src/test/resources/devices.json";

        /* query list of devices in ArrayList */
        ArrayList<Device> devices = TopologyManager.queryDevices("top1");

        /* convert the list of Device to list of object to be able to change to JsonElement */
        ArrayList<Object> deviceList = new ArrayList<>();
        for (Device device : devices) {
            deviceList.add(JsonHelperClass.getDeviceMap(device));
        }
        /* try to write to the destination */
        BufferedWriter writer = new BufferedWriter(new FileWriter(actual));
        writer.write(new GsonBuilder().setPrettyPrinting().create().toJson(deviceList));
        writer.close();

        /* check if the devices are identical of not */
        JsonElement expectedJsonElement = JsonParser.parseReader(new FileReader(expected));
        JsonElement actualJsonElement = JsonParser.parseReader(new FileReader(actual));
        Assert.assertEquals(actualJsonElement, expectedJsonElement);

        /* try to delete this topology */
        TopologyManager.deleteTopology("top1");
    }

    @Test
    public void testQueryDevicesWithNetListNode () throws IOException {
        String topology = "src/main/resources/topology.json";

        /* add new topology to the memory */
        TopologyManager.readJSON(topology);
        String expected = "src/main/resources/devices.json";
        String actual = "src/test/resources/devices.json";

        /* query list of devices in ArrayList */
        ArrayList<Device> devices = TopologyManager.queryDevicesWithNetListNode("top1", "n1");

        /* convert the list of Device to list of object to be able to change to JsonElement */
        ArrayList<Object> deviceList = new ArrayList<>();
        for (Device device : devices) {
            deviceList.add(JsonHelperClass.getDeviceMap(device));
        }
        /* try to write to the destination */
        BufferedWriter writer = new BufferedWriter(new FileWriter(actual));
        writer.write(new GsonBuilder().setPrettyPrinting().create().toJson(deviceList));
        writer.close();

        /* check if the devices are identical of not */
        JsonElement expectedJsonElement = JsonParser.parseReader(new FileReader(expected));
        JsonElement actualJsonElement = JsonParser.parseReader(new FileReader(actual));
        Assert.assertEquals(actualJsonElement, expectedJsonElement);

        /* try to delete this topology */
        TopologyManager.deleteTopology("top1");
    }
}
