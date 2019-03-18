package models;

import org.json.JSONObject;

public class Timeline {
    private String from;
    private String to;

    Timeline(JSONObject obj) {
        from = obj.getString("from");
        to = obj.getString("to");
    }

    public String from() {
        return from;
    }

    public String to() {
        return to;
    }
}
