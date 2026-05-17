package CouponSystemPractice.validation;

public class ValidationResult {
    public boolean isValid;
    public String message;

    public  ValidationResult(boolean isValid,String message){
        this.isValid = isValid;
        this.message = message;
    }

    public static ValidationResult success(){
        return new ValidationResult(true,"Valid");
    }

    public static ValidationResult failure(){
        return new ValidationResult(false,"Invalid");
    }
}
