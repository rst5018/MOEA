package ObjEntity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class Project {

	private int _ID;
	private double _inflationRate;
	private String _startDate;
	private ArrayList<Package> _packages;
	private ArrayList<Contractor> _contractors;
	private ArrayList<Product> _products;
	
	private ArrayList<Product> jarr2ArrProduct (JSONArray jarr){
		ArrayList<Product> result = new ArrayList<Product>();
		for (int i = 0; i < jarr.length(); i++) {
			result.add(new Product(jarr.getJSONObject(i)));
		}
		return result;
	}
	private ArrayList<Contractor> jarr2ArrContractor(JSONArray jarr){
		ArrayList<Contractor> result = new ArrayList<Contractor>();
		for (int i = 0; i < jarr.length(); i++) {
			result.add(new Contractor(jarr.getJSONObject(i), _products));
		}
		return result;
	}
	private ArrayList<Package> jarr2ArrPackage(JSONArray jarr){
		ArrayList<Package> result = new ArrayList<Package>();
		for (int i = 0; i < jarr.length(); i++) {
			result.add(new Package(jarr.getJSONObject(i), _contractors, _products));
		}
		return result;
	}
	
	public Project() {
		
	}
	
	public Project(JSONObject jObj) {
		_ID = jObj.getInt("project_id");
		_inflationRate = jObj.getDouble("inflation_rate");
		_startDate = jObj.getString("start_date");
		_products = jarr2ArrProduct(jObj.getJSONArray("product"));
		_contractors = jarr2ArrContractor(jObj.getJSONArray("contractors"));
		_packages = jarr2ArrPackage(jObj.getJSONArray("packages"));
	}
	
	public int ID(){return _ID;}
	public double InflationRate(){return _inflationRate;}
	public String StartDate(){return _startDate;}
	public ArrayList<Package> Packages(){return _packages;}
	public ArrayList<Contractor> Contractors() {return _contractors;}
	public ArrayList<Product> Products() {return _products;}
	
}
