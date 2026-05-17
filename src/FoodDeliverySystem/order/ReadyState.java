package FoodDeliverySystem.order;

public class ReadyState implements OrderState{
    @Override
    public boolean next(OrderContext orderContext) {
        orderContext.setState(new PickedUpState());
        orderContext.assignDeliveryPartner();
        orderContext.notifyObservers("Order is picked up by delivery partner");
        return true;
    }

    @Override
    public boolean cancel(OrderContext order) {
        return false;
    }
}
