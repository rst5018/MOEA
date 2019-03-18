package ObjEntity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import ObjEntity.Discount;
import util.DateUtil;

public class ProductOfContractor {
	private int _productID;
	private Product _product;
	private double _sellPrice;
	private double _buyPrice;
	private ArrayList<Discount> _discounts = new ArrayList<>();
	private ArrayList<Discount> jarr2ArrDiscount(JSONArray jarr) {
		ArrayList<Discount> result = new ArrayList<Discount>();
		 for (int i = 0; i < jarr.length(); ++i) {
			 result.add(new Discount(jarr.getJSONObject(i)));
	        }
		return result;
	}
	private Product getProductByID (ArrayList<Product> products, int id) {
		Product result;
		for (Product product : products) {
			if(product.ID() == id) {
				return product;
			}
		}
		return null;
	}
	
	public ProductOfContractor() {
		
	}
	public ProductOfContractor(JSONObject jObj) {
		_productID = jObj.getInt("product_id");
		_sellPrice = jObj.getDouble("sell_price");
		_buyPrice = jObj.getDouble("buy_price");
		_discounts = jarr2ArrDiscount(jObj.getJSONArray("discounts"));
		
	}
	public ProductOfContractor(JSONObject jObj, ArrayList<Product> products) {
		_productID = jObj.getInt("product_id");
		_sellPrice = jObj.getDouble("sell_price");
		_buyPrice = jObj.getDouble("buy_price");
		_discounts = jarr2ArrDiscount(jObj.getJSONArray("discounts"));
		_product = getProductByID(products, _productID);
	}
	public int productID() {return _productID;}
	public Product product() {return _product;}
	public double sellPrice() { return _sellPrice;}
	public double buyPrice() {return _buyPrice;}
	public ArrayList<Discount> discounts() {return _discounts;}
	
	public double GetSellPrice (String time) {
		for (Discount discount : _discounts) {
            if (DateUtil.isBetween(time, discount.from(), discount.to())) {
                return _sellPrice * (1 - discount.rate());
            }
        }
        return _sellPrice;
	}
}
