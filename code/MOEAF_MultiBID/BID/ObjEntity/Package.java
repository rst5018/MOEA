package ObjEntity;

import org.json.JSONObject;

import java.util.ArrayList;

public class Package {
    private int packageId;
    private Timeline timeline;
    private ArrayList<Contractor> joinedContractors;
    private ArrayList<PackageProduct> packageProducts;
    private int estimated_cost;

    public Package(JSONObject obj, ArrayList<Contractor> joinedContractors, ArrayList<PackageProduct> packageProducts) {
        packageId = obj.getInt("package_id");
        timeline = new Timeline(obj.getJSONObject("timeline"));
        this.joinedContractors = joinedContractors;
        this.packageProducts = packageProducts;
        estimated_cost = obj.getInt("estimated_cost");
    }

    public int getPackageId() {
        return packageId;
    }

    public Timeline getTimeline() {
        return timeline;
    }

    public ArrayList<Contractor> getJoinedContractors() {
        return joinedContractors;
    }

    public ArrayList<PackageProduct> getPackageProducts() {
        return packageProducts;
    }

    public int getEstimated_cost() {
        return estimated_cost;
    }
}
