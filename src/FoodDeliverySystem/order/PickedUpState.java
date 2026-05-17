package FoodDeliverySystem.order;

public class PickedUpState implements OrderState{
    @Override
    public boolean next(OrderContext orderContext) {
        orderContext.setState(new OnTheWayState());
        orderContext.notifyObservers("Order is on the way...");
        return true;
    }

    @Override
    public boolean cancel(OrderContext order) {
        return false;
    }
}
