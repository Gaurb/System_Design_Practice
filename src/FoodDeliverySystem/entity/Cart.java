package FoodDeliverySystem.entity;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    public String id;
    public User user;
    public Restaurant restaurant;
    public List<CartItem> items;

    public Cart(String id, User user, Restaurant restaurant) {
        this.id = id;
        this.user = user;
        this.restaurant = restaurant;
        this.items = new ArrayList<>();
    }

    public double getSubtotal(){
        double price = 0.0;
        for(CartItem cartItem: items){
            price += cartItem.getTotal();
        }
        return price;
    }
}
