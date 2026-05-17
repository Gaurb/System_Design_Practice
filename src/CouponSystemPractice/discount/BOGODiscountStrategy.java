package CouponSystemPractice.discount;

import CouponSystemPractice.Cart;
import CouponSystemPractice.CartItem;

import java.util.List;

public class BOGODiscountStrategy implements DiscountStrategy{
    public List<String> applicableItems;

    public BOGODiscountStrategy(List<String> applicableItems){
        this.applicableItems = applicableItems;
    }


    @Override
    public double calculateDiscount(Cart cart) {
        double discount = 0;
        for(CartItem cartItem : cart.cartItems ){
            if(applicableItems.contains(cartItem.name)){
                int quantity = cartItem.quantity / 2;
                discount += quantity * cartItem.price;
            }
        }

        return discount;
    }

    @Override
    public String getDescription() {
        return "Buy 1 Get 1 Free.";
    }
}
