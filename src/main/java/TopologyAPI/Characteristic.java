package TopologyAPI;

public class Characteristic {
    private String name;
    private double defaultValue;
    private double minValue;
    private double maxValue;


    public Characteristic () {

    }

    public Characteristic(String name, double defaultValue, double minValue, double maxValue) {
        this.name = name;
        this.defaultValue = defaultValue;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDefaultValue(double defaultValue) {
        this.defaultValue = defaultValue;
    }

    public void setMinValue(double minValue) {
        this.minValue = minValue;
    }

    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }

    public String getName() {
        return name;
    }

    public double getDefaultValue() {
        return defaultValue;
    }

    public double getMinValue() {
        return minValue;
    }

    public double getMaxValue() {
        return maxValue;
    }


}
