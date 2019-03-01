package models;

import org.json.JSONObject;

public class Product {
    private int productId;
    private String description;
    private String unit;

    public Product(JSONObject obj) {
        productId = obj.getInt("product_id");
        description = obj.getString("description");
        unit = obj.getString("unit");
    }

    public int getProductId() {
        return productId;
    }

    public String getDescription() {
        return description;
    }

    public String getUnit() {
        return unit;
    }
}
