package FoodDeliverySystem.delivery;

import FoodDeliverySystem.entity.DeliveryPartner;
import FoodDeliverySystem.entity.Order;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoadBalancedStrategy implements DeliveryAssignmentStrategy {

    // Tracks partner_id -> order count
    private final Map<String, Integer> partnerOrderCount;

    public LoadBalancedStrategy() {
        this.partnerOrderCount = new HashMap<>();
    }

    @Override
    public DeliveryPartner assign(Order order, List<DeliveryPartner> availablePartners) {
        if (availablePartners == null || availablePartners.isEmpty()) {
            return null;
        }

        // Find the partner with the minimum order count
        DeliveryPartner selected = availablePartners.stream()
                .min(Comparator.comparingInt(p -> partnerOrderCount.getOrDefault(p.id, 0)))
                .orElse(availablePartners.getFirst());

        // Increment the count for the selected partner
        partnerOrderCount.put(
                selected.id,
                partnerOrderCount.getOrDefault(selected.id, 0) + 1
        );

        return selected;
    }
}