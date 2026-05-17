package FoodDeliverySystem.order;

public class PreparingState implements OrderState{

    @Override
    public boolean next(OrderContext orderContext) {
        orderContext.setState(new ReadyState());
        orderContext.assignDeliveryPartner();
        orderContext.notifyObservers("Your order is ready");
        return true;
    }

    @Override
    public boolean cancel(OrderContext order) {
        return false;
    }
}
