package CarRentalSystemPractice;

import java.util.HashMap;
import java.util.Map;

public class StandardPricing extends PricingStrategy{

    
    @Override
    public double calculatePrice(CarType carType, int hours) {
        if(hours <= 0) return 0;
        int days = hours / 24;
        int remainingHours = hours % 24;
        if(remainingHours > 10) {
            days++;
            remainingHours = 0;
        }
        return days * rates.get(carType).get("daily") + remainingHours * rates.get(carType).get("hourly");
    }
}
