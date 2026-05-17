package FoodDeliverySystem.delivery;

import FoodDeliverySystem.entity.DeliveryPartner;
import FoodDeliverySystem.entity.Location;
import FoodDeliverySystem.entity.Order;

import java.util.List;

public class NearestPartnerStrategy implements DeliveryAssignmentStrategy{
    @Override
    public DeliveryPartner assign(Order order, List<DeliveryPartner> availablePartner) {
        Location deliveryLocation = order.deliveryAddress;
        double minDist = Double.MAX_VALUE;
        DeliveryPartner selected = null;
        for(DeliveryPartner deliveryPartner: availablePartner){
            double currDist = calculateDistance(deliveryLocation,deliveryPartner.currentLocation);
            if(currDist < minDist ){
                minDist = currDist;
                selected = deliveryPartner;
            }
        }

        return selected;
    }

    private double calculateDistance(Location l1,Location l2){
        float latDiff = (l1.Latitude - l2.longitude);
        float longDiff = (l1.longitude - l2.longitude);
        return Math.sqrt(latDiff*latDiff + longDiff*longDiff);
    }
}
