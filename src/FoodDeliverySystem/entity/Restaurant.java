package FoodDeliverySystem.entity;

import java.util.List;

public class
Restaurant {
    public String id;
    public String name;
    public Location location;
    public List<String> cuisines;
    public float rating;
    public boolean isOpen;
    public float miniOrderAmount;
    public int avgPrepTimeMiss;
    public List<MenuItem> menu;

    public Restaurant(String id, String name, Location location, List<String> cuisines, float rating, boolean isOpen, float miniOrderAmount, int avgPrepTimeMiss) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.cuisines = cuisines;
        this.rating = rating;
        this.isOpen = isOpen;
        this.miniOrderAmount = miniOrderAmount;
        this.avgPrepTimeMiss = avgPrepTimeMiss;
    }
}
