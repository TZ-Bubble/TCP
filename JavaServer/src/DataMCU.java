public class DataMCU {
    private float enviorenmentalTemprature;
    private float enviorenmentalHumidity;
    private float weight;
    private boolean state;
    private long time;
    private double longitude;
    private double latitude;
    private float boxTemprature;
    private float settedTemprature;

    public DataMCU(String message) {
        this.enviorenmentalTemprature = Float.parseFloat(message.substring(message.indexOf("E-TEMP") + 7, message.indexOf("E-HUMI") - 1));
        this.enviorenmentalHumidity = Float.parseFloat(message.substring(message.indexOf("E-HUMI") + 7, message.indexOf("WEIGHT") - 1));
        this.weight = Float.parseFloat(message.substring(message.indexOf("WEIGHT") + 7, message.indexOf("ISOPEN") - 1));
        this.state = Integer.parseInt(message.substring(message.indexOf("ISOPEN") + 7, message.indexOf("LONGITUDE") - 1)) == 0 ? false : true;
        this.longitude = Float.parseFloat(message.substring(message.indexOf("LONGITUDE") + 10, message.indexOf("LATITUDE") - 1));
        this.latitude = Float.parseFloat(message.substring(message.indexOf("LATITUDE") + 9, message.indexOf("B-TEMP") - 1));
        this.boxTemprature = Float.parseFloat(message.substring(message.indexOf("B-TEMP") + 7, message.indexOf("SET-TEMP") - 1));
        this.settedTemprature = Float.parseFloat(message.substring(message.indexOf("SET-TEMP") + 9, message.indexOf("END") - 1));
    }

    public float getEnviorenmentalTemprature() {
        return enviorenmentalTemprature;
    }

    public float getEnviorenmentalHumidity() {
        return enviorenmentalHumidity;
    }

    public float getWeight() {
        return weight;
    }

    public boolean getState() {
        return state;
    }

    public long getTime() {
        return time;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public float getBoxTemprature() {
        return boxTemprature;
    }

    public float getSettedTemprature() {
        return settedTemprature;
    }

}
