package FoodDeliverySystem.delivery;

import FoodDeliverySystem.entity.DeliveryPartner;
import FoodDeliverySystem.entity.Order;

import java.util.Comparator;
import java.util.List;

public class HighestRatedPartnerStrategy implements DeliveryAssignmentStrategy{
    @Override
    public DeliveryPartner assign(Order order, List<DeliveryPartner> availablePartner) {
        if(availablePartner == null || availablePartner.isEmpty()){
            return null;
        }

        return availablePartner.stream()
                .max(Comparator.comparingDouble(p -> p.rating))
                .orElse(null);
    }
}
