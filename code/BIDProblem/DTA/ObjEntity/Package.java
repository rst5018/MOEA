package ObjEntity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;


public class Package {

	private int _ID;
	private String _from;
	private String _to;
	private ArrayList<Contractor> _joinedContractors;
	private ArrayList<ProductOfPackage> _products;
	private double _estimatedCost;
	
	private ArrayList<Contractor> jarr2ArrjoinedContractor (JSONArray jarr,ArrayList<Contractor> contractors){
		ArrayList<Contractor> result = new ArrayList<>();
		for (int i =0; i< jarr.length(); i++) {
			for (Contractor contractor : contractors) {
				if(contractor.ID() == jarr.getInt(i)) {
					result.add(contractor);
					break;
				}
			}
		}
		return result;
	}
	private ArrayList<ProductOfPackage> jarr2ArrProductOfPackage(JSONArray jarr, ArrayList<Product>products){
		ArrayList<ProductOfPackage> result = new ArrayList<>();
		if(products == null) {
			for (int i = 0; i < jarr.length(); i++) {
				result.add(new ProductOfPackage(jarr.getJSONObject(i)));
			}
		}
		else{
			for (int i = 0; i < jarr.length(); i++) {
				result.add(new ProductOfPackage(jarr.getJSONObject(i), products));
			}
		}				
		return result;
	}
	
	public Package() {}
	
	public Package(JSONObject jObj, ArrayList<Contractor> contractors) {
		_ID = jObj.getInt("package_id");
		JSONObject timeline = jObj.getJSONObject("timeline");
		_from = timeline.getString("from");
		_to = timeline.getString("to");
		_joinedContractors = jarr2ArrjoinedContractor(jObj.getJSONArray("joined_contractors"), contractors);
		_products = jarr2ArrProductOfPackage(jObj.getJSONArray("products"), null);
		_estimatedCost = jObj.getDouble("estimated_cost");
	}
	
	public Package(JSONObject jObj, ArrayList<Contractor> contractors, ArrayList<Product> products) {
		_ID = jObj.getInt("package_id");
		JSONObject timeline = jObj.getJSONObject("timeline");
		_from = timeline.getString("from");
		_to = timeline.getString("to");
		_joinedContractors = jarr2ArrjoinedContractor(jObj.getJSONArray("joined_contractors"), contractors);
		_products = jarr2ArrProductOfPackage(jObj.getJSONArray("products"), products);
		_estimatedCost = jObj.getDouble("estimated_cost");
	}
	
	public int ID() {return _ID;}
	public String from() {return _from;}
	public String to() {return _to;}
	public ArrayList<Contractor> joinedContractors() {return _joinedContractors;}
	public ArrayList<ProductOfPackage> Products() {return _products;}
	public double estimatedCost() {return _estimatedCost;}
	
	public Contractor getContractorByIndex(int indexJoinedContractor) {
		return _joinedContractors.get(indexJoinedContractor);
	}
	
	public Contractor getContractorByID (int contractorID) {
		for (Contractor contractor : _joinedContractors) 
			if(contractor.ID() == contractorID) return contractor;
		return null;
	}
}
