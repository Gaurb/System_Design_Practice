package FoodDeliverySystem.delivery;

import FoodDeliverySystem.entity.DeliveryPartner;
import FoodDeliverySystem.entity.Order;

import java.util.List;

public interface DeliveryAssignmentStrategy {
    DeliveryPartner assign(Order order, List<DeliveryPartner> availablePartner);
}
