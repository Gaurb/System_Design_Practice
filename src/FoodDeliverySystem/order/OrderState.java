package FoodDeliverySystem.order;

public interface OrderState {
    boolean next(OrderContext orderContext);
    boolean cancel(OrderContext order);
}
