package FoodDeliverySystem.order;

import FoodDeliverySystem.delivery.DeliveryAssignmentStrategy;
import FoodDeliverySystem.delivery.NearestPartnerStrategy;
import FoodDeliverySystem.entity.Order;
import FoodDeliverySystem.enums.OrderStatus;
import FoodDeliverySystem.observers.OrderObserver;

import java.util.ArrayList;
import java.util.List;

public class OrderContext {
    public Order order;
    public OrderState state;
    public List<OrderObserver> observers;
    public DeliveryAssignmentStrategy deliveryAssignmentStrategy;

    public OrderContext(Order order, DeliveryAssignmentStrategy deliveryAssignmentStrategy) {
        this.order = order;
        this.state = new PlacedState();
        this.observers = new ArrayList<>();
        this.deliveryAssignmentStrategy = deliveryAssignmentStrategy;
    }

    public OrderContext(Order order) {
        this.order = order;
        this.state = new PlacedState();
        this.observers = new ArrayList<>();
        this.deliveryAssignmentStrategy = new NearestPartnerStrategy();
    }

    public void setState(OrderState state) {
        this.state = state;
        order.status = getStatusEnum();
    }

    OrderStatus getStatusEnum() {
        if (state instanceof PlacedState)
            return OrderStatus.PLACED;
        else if (state instanceof CancelledState)
            return OrderStatus.CANCELED;
        else if (state instanceof AcceptedState)
            return OrderStatus.ACCEPTED;
        else if (state instanceof PreparingState)
            return OrderStatus.PREPARING;
        else if (state instanceof ReadyState)
            return OrderStatus.READY;
        else if (state instanceof PickedUpState)
            return OrderStatus.PICKED_UP;
        else if (state instanceof OnTheWayState)
            return OrderStatus.ON_THE_WAY;
        else if (state instanceof DeliveryState)
            return OrderStatus.DELIVERED;
        return null;
    }

    public boolean nextStatus() {
        return this.state.next(this);
    }

    public boolean cancelOrder() {
        return this.state.cancel(this);
    }

    public void addObserver(OrderObserver observer) {
        observers.add(observer);
    }

    public void notifyObservers(String message) {
        for (OrderObserver observer : observers) {
            observer.update(this.order.id, message);
        }
    }

    public void processRefund() {
        System.out.println("processing refund");
    }

    public void assignDeliveryPartner() {
        order.deliveryPartner = deliveryAssignmentStrategy.assign(order, List.of());
    }
}
