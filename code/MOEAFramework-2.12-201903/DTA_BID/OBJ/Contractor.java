package OBJ;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class Contractor {
	private int _ID;
	private String _description;
	private double _quality;
	private double _relationship;
	private ArrayList<ProductOfContractor> _products;
	
	private ArrayList<ProductOfContractor> jarr2ArrProductOfContructor(JSONArray jarr, ArrayList<Product> products){
		ArrayList<ProductOfContractor> result = new ArrayList<>();
		if(products == null) {
			for (int i = 0; i < jarr.length(); ++i) {
				 result.add(new ProductOfContractor(jarr.getJSONObject(i)));
		        }
		} else {
			for (int i = 0; i < jarr.length(); ++i) {
				 result.add(new ProductOfContractor(jarr.getJSONObject(i), products));
		        }
		}
		
		return result;
	}
	
	public Contractor () {}
	
	public Contractor (JSONObject jObj) {
		_ID = jObj.getInt("contractor_id");
		_description = jObj.getString("description");
		_quality = jObj.getDouble("quality");
		_relationship = jObj.getDouble("relationship");
		_products = jarr2ArrProductOfContructor(jObj.getJSONArray("products"), null);		
	}
	
	public Contractor (JSONObject jObj, ArrayList<Product> products) {
		_ID = jObj.getInt("contractor_id");
		_description = jObj.getString("description");
		_quality = jObj.getDouble("quality");
		_relationship = jObj.getDouble("relationship");
		_products = jarr2ArrProductOfContructor(jObj.getJSONArray("products"), products);	
		
	}
	
	public int ID() {return _ID;}
	public String description() {return _description;}
	public double quality() {return _quality;}
	public double relationship() {return _relationship;}
	public ArrayList<ProductOfContractor> Products() {return _products;}
	
	public ProductOfContractor GetProductOfContractorByID (int productID) {
		for (ProductOfContractor productOfContractor : _products) {
			if(productOfContractor.productID() == productID)
				return productOfContractor;
		}
		return null;
	}
	
}
