package ObjEntity;

import org.json.JSONObject;

public class Product {
	private int _ID;
	private String _Name;
	private String _Description;
	private String _Unit;
	
	public Product() {
		
	}
	public Product(JSONObject obj) {
		_ID = obj.getInt("product_id");
		//if(obj.getString("product_name") != null) _Name = obj.getString("product_name");
        _Description = obj.getString("description");
        _Unit = obj.getString("unit");
	}
	
	public int ID() {
		return _ID;
	}
	public String Name(){
		return _Name;
	}
	public String Description(){
		return _Description;
	}
	public String Unit(){
		return _Unit;
	}
	
	
}
