package models;

public class PackageProduct {
    private Product product;
    private int quantity;

    public PackageProduct(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }
}
