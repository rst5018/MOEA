package ObjEntity;

import org.json.JSONObject;

public class Discount {
    private String from;
    private String to;
    private double rate;

    Discount(JSONObject obj) {
        from = obj.getString("from");
        to = obj.getString("to");
        rate = obj.getDouble("rate");
    }

    public String from() {
        return from;
    }

    public String to() {
        return to;
    }

    public double getRate() {
        return rate;
    }
}
