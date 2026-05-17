package CouponSystemPractice;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    public List<CartItem> cartItems = new ArrayList<>();
    public double deliveryFee = 40.0;

    public double getSubTotal(){
        double sum = 0;
        for(CartItem cartItem: cartItems){
            sum += cartItem.price* cartItem.quantity;
        }
        return sum;
    }

    public double getTotal(){
        return getSubTotal()+deliveryFee;
    }

    public void addCartItem(CartItem cartItem){
        cartItems.add(cartItem);
    }

}
