package FoodDeliverySystem.observers;

public class CustomerNotifier implements OrderObserver{
    @Override
    public void update(String orderId, String message) {
        System.out.println("Customer notification: "+ message);
    }
}
