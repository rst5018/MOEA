package OBJ;

import java.util.ArrayList;

import org.json.JSONObject;

public class ProductOfPackage {
	private int _productID;
	private Product _product;
	private int _quantity;
	private Product getProductByID (ArrayList<Product> products, int id) {
		Product result;
		for (Product product : products) {
			if(product.ID() == id) {
				return product;
			}
		}
		return null;
	}
	public ProductOfPackage() {}
	public ProductOfPackage(JSONObject jObj) {
		_productID = jObj.getInt("product_id");
		_quantity = jObj.getInt("quantity");
	}
	public ProductOfPackage(JSONObject jObj, ArrayList<Product> products) {
		_productID = jObj.getInt("product_id");
		_quantity = jObj.getInt("quantity");
		_product = getProductByID(products, _productID);
	}
	public int productID() {return _productID;}
	public Product product() {return _product;}
	public int quantity() {return _quantity;}
}
