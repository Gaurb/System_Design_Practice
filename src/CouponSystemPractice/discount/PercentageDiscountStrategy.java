package CouponSystemPractice.discount;

import CouponSystemPractice.Cart;

public class PercentageDiscountStrategy implements DiscountStrategy{

    public double percentage;
    public double maxDiscount;

    public PercentageDiscountStrategy(double percentage,double maxDiscount){
        this.percentage = percentage;
        this.maxDiscount = maxDiscount;
    }

    @Override
    public double calculateDiscount(Cart cart) {
        return Math.min(maxDiscount,cart.getSubTotal() * percentage / 100);
    }

    @Override
    public String getDescription() {
        String desc = percentage+" % off";
        desc += "up to "+ maxDiscount+" Rs.";
        return desc;
    }
}
