package FoodDeliverySystem.observers;

public class RestaurantNotifier implements OrderObserver{
    @Override
    public void update(String orderId, String message) {
        System.out.println("Restaurant Dashboard: " + message);
    }
}
