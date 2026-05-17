package CarRentalSystemPractice;

public class User {
    String name;
    String email;
    String phoneNumber;
    String licenseNumber;
    int age;

    public User(String name, String email, String phoneNumber, String licenseNumber,int age) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.licenseNumber = licenseNumber;
        this.age = age;
    }

    boolean isEligibleToRent() {
        return age >= 18 && licenseNumber != null && !licenseNumber.isEmpty();
    }
}
