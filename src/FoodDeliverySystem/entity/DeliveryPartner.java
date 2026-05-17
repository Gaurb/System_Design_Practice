package FoodDeliverySystem.entity;

import FoodDeliverySystem.enums.DeliveryPartnerStatus;

public class DeliveryPartner {
    public String id;
    public String name;
    public String phone;
    public Location currentLocation;
    public DeliveryPartnerStatus status;
    public double rating;
    public String vehicleType;

    public DeliveryPartner(String id, String name, String phone, Location currentLocation, DeliveryPartnerStatus status, double rating, String vehicleType) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.currentLocation = currentLocation;
        this.status = status;
        this.rating = rating;
        this.vehicleType = vehicleType;
    }
}
