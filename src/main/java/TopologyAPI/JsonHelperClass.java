package TopologyAPI;

import com.google.gson.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class JsonHelperClass {
    private JsonHelperClass() {

    }

    public static JsonObject createJsonObject (String filePath) throws IOException {
        File input = new File(filePath);
        JsonElement jsonElement = JsonParser.parseReader(new FileReader(input));
        return jsonElement.getAsJsonObject();
    }

    public static String getTopologyID (JsonObject jsonObject) {
        return getElement(jsonObject, "id");
    }

    public static ArrayList<Device> getTopologyDevices (JsonObject jsonObject) {
        JsonArray components = jsonObject.get("components").getAsJsonArray();
        ArrayList<Device> devices = new ArrayList<>();
        for (JsonElement component : components) {
            devices.add(getDevice(component.getAsJsonObject()));
        }
        return devices;
    }

    public static HashMap<String, Object> convertTopologyToMap (Topology topology) {
        HashMap<String, Object> topologyMap = new HashMap<>();
        topologyMap.put("id", topology.getID());
        topologyMap.put("components", getDeviceList(topology));
        return topologyMap;
    }

    public static void writeMapToJSON (HashMap<String, Object> jsonMap, String filePath) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
        writer.write(new GsonBuilder().setPrettyPrinting().create().toJson(jsonMap));
        writer.close();
    }

    public static HashMap<String, Object> getDeviceMap (Device device) {
        HashMap<String, Object> deviceMap = new HashMap<>();
        deviceMap.put("type", device.getType());
        deviceMap.put("id", device.getID());
        deviceMap.put(device.getCharacteristics().getName(), getCharacteristicsMap(device.getCharacteristics()));
        deviceMap.put("netlist", device.getNetList());
        return deviceMap;
    }

    private static ArrayList<Object> getDeviceList (Topology topology) {
        ArrayList<Device> devices = topology.getComponents();
        ArrayList<Object> devicesList = new ArrayList<>();
        for (Device device : devices) {
            devicesList.add(getDeviceMap(device));
        }
        return devicesList;
    }

    private static HashMap<String, Double> getCharacteristicsMap (Characteristic characteristic) {
        HashMap<String, Double> characteristicMap = new HashMap<>();
        characteristicMap.put("default", characteristic.getDefaultValue());
        characteristicMap.put("min", characteristic.getMinValue());
        characteristicMap.put("max", characteristic.getMaxValue());
        return characteristicMap;
    }

    private static String getElement (JsonObject jsonObject, String element) {
        return jsonObject.get(element).getAsString();
    }

    private static JsonObject getCharacteristicsObject(JsonObject jsonObject, String key) {
        return jsonObject.get(key).getAsJsonObject();
    }

    private static JsonObject getNetlistObject(JsonObject jsonObject) {
        return jsonObject.get("netlist").getAsJsonObject();
    }

    private static Characteristic getCharacteristics (JsonObject jsonObject, String characteristicKey) {
        JsonObject characteristicValue = getCharacteristicsObject(jsonObject, characteristicKey);
        HashMap<String, Object> characteristicsMap = new Gson().fromJson(characteristicValue, HashMap.class);
        double defaultValue = 0;
        double minValue = 0;
        double maxValue = 0;
        for (Map.Entry<String, Object> entry : characteristicsMap.entrySet()) {
            String key = entry.getKey();
            double value = (double) entry.getValue();
            switch (key) {
                case "default" -> defaultValue = value;
                case "min" -> minValue = value;
                case "max" -> maxValue = value;
                default -> {
                }
            }
        }
        return new Characteristic(characteristicKey, defaultValue, minValue, maxValue);
    }

    private static HashMap<String, String> getNetlist (JsonObject jsonObject) {
        JsonObject netListObject = getNetlistObject(jsonObject);
        return new Gson().fromJson(netListObject, HashMap.class);
}

    private static Device getDevice (JsonObject jsonObject) {
        HashMap<String, Object> deviceMap = new Gson().fromJson(jsonObject, HashMap.class);
        String type = null, ID = null;
        HashMap<String, String> netList= null;
        Characteristic characteristics = null;
        for (Map.Entry<String, Object> entry : deviceMap.entrySet()) {
            String key = entry.getKey();
            switch (key) {
                case "type" -> type = getElement(jsonObject, key);
                case "id" -> ID = getElement(jsonObject, key);
                case "netlist" -> netList = getNetlist(jsonObject);
                default -> characteristics = getCharacteristics(jsonObject, key);
            }
        }
        return new Device(type, ID, characteristics, netList);
    }
}

