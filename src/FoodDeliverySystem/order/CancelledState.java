package FoodDeliverySystem.order;

public class CancelledState implements OrderState{
    @Override
    public boolean next(OrderContext orderContext) {
        return false;
    }

    @Override
    public boolean cancel(OrderContext order) {
        return false;
    }
}
