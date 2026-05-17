package CouponSystemPractice;

public class User {
    String id;
    String name;
    String email;
    int orderCount;
    boolean isPrime;

    public User(String name, String email) {
        this.id = java.util.UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        orderCount = 0;
        isPrime = false;
    }
}
