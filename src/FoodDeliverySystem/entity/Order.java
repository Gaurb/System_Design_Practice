package FoodDeliverySystem.entity;

import FoodDeliverySystem.enums.OrderStatus;
import FoodDeliverySystem.enums.PaymentStatus;

import java.util.ArrayList;
import java.util.List;

public class Order {
    public String id;
    public User user;
    public Restaurant restaurant;
    public List<CartItem> items;
    public Location deliveryAddress;
    public OrderStatus status;
    public DeliveryPartner deliveryPartner;
    public PaymentStatus paymentStatus;
    public double amount;

    public Order(String id, User user, Restaurant restaurant, Location deliveryAddress, OrderStatus status, PaymentStatus paymentStatus,double amount) {
        this.id = id;
        this.user = user;
        this.restaurant = restaurant;
        this.items = new ArrayList<>();
        this.deliveryAddress = deliveryAddress;
        this.status = status;
        this.paymentStatus = paymentStatus;
        this.amount = amount;
    }

}
