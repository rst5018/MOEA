package models;

import org.json.JSONArray;
import org.json.JSONObject;
import util.DateUtil;

import java.util.ArrayList;

public class ConstractorProduct {

    private Product product;
    private int sellPrice;
    private int buyPrice;
    private ArrayList<Discount> discounts = new ArrayList<>();

    public ConstractorProduct(Product product, JSONObject obj) {
        this.product = product;
        sellPrice = obj.getInt("sell_price");
        buyPrice = obj.getInt("buy_price");
        JSONArray discountsArray = obj.getJSONArray("discounts");
        for (int i = 0; i < discountsArray.length(); ++i) {
            discounts.add(new Discount(discountsArray.getJSONObject(i)));
        }
    }

    public Product getProduct() {
        return product;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public int getBuyPrice() {
        return buyPrice;
    }

    public ArrayList<Discount> getDiscounts() {
        return discounts;
    }

    public double getSellPriceAt(String time) {
        for (Discount discount : discounts) {
            if (DateUtil.isBetween(time, discount.from(), discount.to())) {
                return sellPrice * (1 - discount.getRate());
            }
        }
        return sellPrice;
    }
}
