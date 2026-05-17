package FoodDeliverySystem.observers;

public class DeliveryPartnerNotifier implements OrderObserver{
    @Override
    public void update(String orderId, String message) {
        System.out.println("Delivery Partner: "+ message);
    }
}
