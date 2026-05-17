package FoodDeliverySystem.order;

public class OnTheWayState implements OrderState{
    @Override
    public boolean next(OrderContext orderContext) {
        orderContext.setState(new DeliveryState());
        orderContext.notifyObservers("Order is delivered by the partner");
        return true;
    }

    @Override
    public boolean cancel(OrderContext order) {
        return false;
    }
}
