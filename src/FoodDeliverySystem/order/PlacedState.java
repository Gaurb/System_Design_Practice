package FoodDeliverySystem.order;

public class PlacedState implements OrderState{

    @Override
    public boolean next(OrderContext orderContext) {
        orderContext.setState(new AcceptedState());
        orderContext.notifyObservers("Order is Placed");
        return  true;
    }

    @Override
    public boolean cancel(OrderContext order) {
        order.setState(new CancelledState());
        order.processRefund();
        return true;
    }
}
