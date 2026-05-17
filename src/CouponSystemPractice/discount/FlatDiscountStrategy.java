package CouponSystemPractice.discount;

import CouponSystemPractice.Cart;

public class FlatDiscountStrategy implements DiscountStrategy{
    double amount;
    public FlatDiscountStrategy(double amount){
        this.amount = amount;
    }


    @Override
    public double calculateDiscount(Cart cart) {
        return Math.min(this.amount,cart.getSubTotal());
    }

    @Override
    public String getDescription() {
        return "Flat discount of Rs "+ this.amount;
    }
}
