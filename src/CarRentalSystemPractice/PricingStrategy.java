package CarRentalSystemPractice;

import java.util.HashMap;
import java.util.Map;

public abstract class PricingStrategy {
    Map<CarType, Map<String,Integer>> rates;

    public PricingStrategy(){
        rates = new HashMap<>();
        rates.putIfAbsent(CarType.HATCHBACK,new HashMap<>());
        rates.get(CarType.HATCHBACK).put("hourly",50);
        rates.get(CarType.HATCHBACK).put("daily",500);
        rates.putIfAbsent(CarType.SEDAN,new HashMap<>());
        rates.get(CarType.SEDAN).put("hourly",70);
        rates.get(CarType.SEDAN).put("daily",700);
        rates.putIfAbsent(CarType.SUV,new HashMap<>());
        rates.get(CarType.SUV).put("hourly",100);
        rates.get(CarType.SUV).put("daily",1000);
        rates.putIfAbsent(CarType.MICRO,new HashMap<>());
        rates.get(CarType.MICRO).put("hourly",30);
        rates.get(CarType.MICRO).put("daily",300);
        rates.putIfAbsent(CarType.OFF_ROAD,new HashMap<>());
        rates.get(CarType.OFF_ROAD).put("hourly",120);
        rates.get(CarType.OFF_ROAD).put("daily",1200);
    }
    abstract double calculatePrice(CarType carType, int hours);
}
