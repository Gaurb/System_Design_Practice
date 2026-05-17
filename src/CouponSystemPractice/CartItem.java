package CouponSystemPractice;

public class CartItem {
    public String name;
    public double price;
    public int quantity;
    public String category;

    public CartItem(String name, double price, int quantity, String category) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
    }
}
