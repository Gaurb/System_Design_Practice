package FoodDeliverySystem;

import FoodDeliverySystem.delivery.DeliveryAssignmentStrategy;
import FoodDeliverySystem.delivery.NearestPartnerStrategy;
import FoodDeliverySystem.entity.*;
import FoodDeliverySystem.enums.DeliveryPartnerStatus;
import FoodDeliverySystem.enums.OrderStatus;
import FoodDeliverySystem.enums.PaymentStatus;
import FoodDeliverySystem.observers.CustomerNotifier;
import FoodDeliverySystem.observers.DeliveryPartnerNotifier;
import FoodDeliverySystem.observers.RestaurantNotifier;
import FoodDeliverySystem.order.OrderContext;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FoodDeliveryService {
    private static volatile FoodDeliveryService instance;
    private static final Lock lock = new ReentrantLock();

    private FoodDeliveryService() {
    }

    public static FoodDeliveryService getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new FoodDeliveryService();
                }
            }
        }
        return instance;
    }


    private Map<String, Restaurant> restaurants = new ConcurrentHashMap<>();
    private Map<String, OrderContext> orders = new ConcurrentHashMap<>();
    private Map<String, DeliveryPartner> deliveryPartners = new ConcurrentHashMap<>();
    private Map<String, Cart> carts = new ConcurrentHashMap<>();
    private DeliveryAssignmentStrategy deliveryAssignmentStrategy = new NearestPartnerStrategy();
    private final Lock orderLock = new ReentrantLock();


    public List<Restaurant> getNearBuRestaurants(Location location, double radius) {
        List<Restaurant> nearBy = new ArrayList<>();

        for (Restaurant restaurant : restaurants.values()) {
            if (restaurant.isOpen) {
                double distance = calculateDistance(location, restaurant.location);
                if (distance <= radius) nearBy.add(restaurant);
            }
        }
        nearBy.sort(Comparator.comparingDouble(a -> a.rating));
        return nearBy;
    }

    public List<MenuItem> getRestaurantMenu(String restaurantId) {
        Restaurant restaurant = restaurants.get(restaurantId);
        if (restaurant == null) {
            return Collections.emptyList();
        }
        List<MenuItem> menu = new ArrayList<>();
        for (MenuItem menuItem : restaurant.menu) {
            if (menuItem.isAvailable) {
                menu.add(menuItem);
            }
        }
        return menu;
    }

    public Cart addToCart(User user, String restaurantId, String itemId, int quantity) {
        String cartId = "CART_" + user.getId();
        carts.putIfAbsent(cartId, new Cart(cartId, user, restaurants.get(restaurantId)));

        Cart cart = carts.get(cartId);

        if (!cart.restaurant.id.equals(restaurantId))
            throw new RuntimeException("Cannot add items from another restaurant.");

        MenuItem selected = null;
        for (MenuItem item : cart.restaurant.menu) {
            if (item.id.equals(itemId)) {
                selected = item;
                break;
            }
        }

        if (selected != null) {
            cart.items.add(new CartItem(selected, quantity));
        }
        return cart;
    }

    public Order placeOrder(String cartId, Location delivery, String couponCode) {
        orderLock.lock();
        try {
            Cart cart = carts.get(cartId);
            if (cart == null) {
                throw new RuntimeException("Cart not found");
            }

            double itemTotal = cart.getSubtotal();
            double deliveryFee = calculateDeliveryFee(cart.restaurant.location,delivery);
            double taxes = itemTotal * 0.05;
            double discount = applyCoupon(couponCode,itemTotal);

            double total = itemTotal + deliveryFee + taxes - discount;

            Order order = new Order(UUID.randomUUID().toString(),
                    cart.user,cart.restaurant,delivery, OrderStatus.PLACED,
                    PaymentStatus.COMPLETE,total);

            OrderContext orderContext = new OrderContext(order);
            orderContext.addObserver(new CustomerNotifier());
            orderContext.addObserver(new RestaurantNotifier());
            carts.remove(cartId);
            orders.put(order.id, orderContext);
            return order;
        } finally {
            orderLock.unlock();
        }
    }

    public DeliveryPartner assignDeliveryPartner(
            String orderId
    ){
        OrderContext orderContext = orders.get(orderId);
        if(orderContext == null) return null;
        Order order = orderContext.order;

        List<DeliveryPartner> available = new ArrayList<>();

        for(DeliveryPartner deliveryPartner: deliveryPartners.values()){
            if(deliveryPartner.status.equals(DeliveryPartnerStatus.AVAILABLE)){
                available.add(deliveryPartner);
            }
        }

        DeliveryPartner partner = deliveryAssignmentStrategy.assign(order,available);

        if(partner != null){
            partner.status = DeliveryPartnerStatus.BUSY;
            order.deliveryPartner = partner;
            orderContext.addObserver(new DeliveryPartnerNotifier());
        }

        return partner;
    }


    private double calculateDistance(Location l1, Location l2) {
        float latDiff = (l1.Latitude - l2.longitude);
        float longDiff = (l1.longitude - l2.longitude);
        return Math.sqrt(latDiff * latDiff + longDiff * longDiff);
    }

    private double calculateDeliveryFee(
            Location rest,
            Location delivery) {

        double distance =
                calculateDistance(
                        rest,
                        delivery);

        return 20 + distance * 5;
    }

    public boolean updateOrderStatus(String orderId){
        OrderContext ctx = orders.get(orderId);
        return ctx!= null && ctx.nextStatus();
    }

    public boolean cancelOrder(String orderId){
        OrderContext orderContext = orders.get(orderId);
        return orderContext != null && orderContext.cancelOrder();
    }


    private double applyCoupon(
            String coupon,
            double amount) {

        Map<String, Integer> coupons =
                Map.of(
                        "FIRST50", 50,
                        "FLAT100", 100
                );

        return coupons.getOrDefault(
                coupon,
                0);
    }

}
