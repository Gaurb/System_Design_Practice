package FoodDeliverySystem.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User {
    String id;
    String name;
    String email;
    String phone;
    List<Location> addresses;

    public User(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.addresses = new ArrayList<>();
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Location> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Location> addresses) {
        this.addresses = addresses;
    }

    public void addAddress(Location location){
        addresses.add(location);
    }

}
