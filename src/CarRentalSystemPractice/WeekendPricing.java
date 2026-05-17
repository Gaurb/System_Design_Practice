package CarRentalSystemPractice;

public class WeekendPricing extends PricingStrategy{
    StandardPricing standardPricing = new StandardPricing();
    @Override
    public double calculatePrice(CarType carType, int hours) {
        double basePrice = standardPricing.calculatePrice(carType, hours);
        return basePrice * 1.2; // 20% increase for weekend pricing
    }
}
