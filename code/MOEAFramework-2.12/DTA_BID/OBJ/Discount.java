package OBJ;

import org.json.JSONObject;

public class Discount {
    private String _from;
    private String _to;
    private double _rate;

    Discount(JSONObject obj) {
        _from = obj.getString("from");
        _to = obj.getString("to");
        _rate = obj.getDouble("rate");
    }

    public String from() {
        return _from;
    }

    public String to() {
        return _to;
    }

    public double rate() {
        return _rate;
    }
}
