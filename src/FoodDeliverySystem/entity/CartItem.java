package FoodDeliverySystem.entity;

import java.util.ArrayList;
import java.util.List;

public class CartItem {
    public MenuItem menuItem;
    public int quantity;
    List<String> customization;

    public CartItem(MenuItem menuItem, int quantity) {
        this.menuItem = menuItem;
        this.quantity = quantity;
        this.customization = new ArrayList<>();
    }

    public double getTotal(){
        double base = menuItem.price * quantity;
        if(!customization.isEmpty()) base += 30.0;
        return base;
    }
}
