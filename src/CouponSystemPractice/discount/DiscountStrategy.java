package CouponSystemPractice.discount;

import CouponSystemPractice.Cart;

public interface DiscountStrategy {
    double calculateDiscount(Cart cart);
    String getDescription();
}
