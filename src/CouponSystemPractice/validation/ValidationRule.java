package CouponSystemPractice.validation;

import CarRentalSystemPractice.User;
import CouponSystemPractice.Cart;
import CouponSystemPractice.Coupon;

public abstract class ValidationRule {
    ValidationResult nextRule;

    public ValidationRule(ValidationResult nextRule){
        this.nextRule = nextRule;
    }

    public ValidationRule(){
        this.nextRule = null;
    }

    public abstract ValidationRule validate(User user, Cart cart, Coupon coupon);
}
