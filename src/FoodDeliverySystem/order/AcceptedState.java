package FoodDeliverySystem.order;

public class AcceptedState implements OrderState{
    @Override
    public boolean next(OrderContext orderContext) {
        orderContext.setState(new PreparingState());
        orderContext.notifyObservers("Restaurant is preparing your order");
        return true;
    }

    @Override
    public boolean cancel(OrderContext order) {
        order.setState(new CancelledState());
        order.processRefund();
        return true;
    }
}
