package models;

import org.json.JSONObject;

import java.util.ArrayList;

public class Contractor {
    private int contractorId;
    private String description;
    private double quality;
    private double relationship;
    private ArrayList<ConstractorProduct> constractorProducts;

    public Contractor(JSONObject obj, ArrayList<ConstractorProduct> constractorProducts) {
        contractorId = obj.getInt("contractor_id");
        description = obj.getString("description");
        quality = obj.getDouble("quality");
        relationship = obj.getDouble("relationship");
        this.constractorProducts = constractorProducts;
    }

    public int getContractorId() {
        return contractorId;
    }

    public String getDescription() {
        return description;
    }

    public double getQuality() {
        return quality;
    }

    public double getRelationship() {
        return relationship;
    }

    public ArrayList<ConstractorProduct> getConstractorProducts() {
        return constractorProducts;
    }

    public ConstractorProduct getConstractorProduct(int id) {
        for (ConstractorProduct constractorProduct : constractorProducts) {
            if (constractorProduct.getProduct().getProductId() == id) {
                return constractorProduct;
            }
        }
        return null;
    }
}
