package CouponSystemPractice.discount;

import CouponSystemPractice.Cart;

public class FreeDeliveryStrategy implements DiscountStrategy{
    @Override
    public double calculateDiscount(Cart cart) {
        return cart.deliveryFee;
    }

    @Override
    public String getDescription() {
        return "Free delivery";
    }
}
