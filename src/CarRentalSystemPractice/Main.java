package CarRentalSystemPractice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        logger.info("===== Car Rental System Started =====");

        CarRentalService service = new CarRentalService();

        // Add cars
        logger.info("Adding cars to system...");
        Car car1 = new Car("DL01AB1234", CarType.HATCHBACK, "Maruti Swift");
        Car car2 = new Car("DL01CD1234", CarType.SEDAN, "Honda City");
        Car car3 = new Car("DL01EF1234", CarType.SUV, "Toyota Fortuner");
        service.addCar(car1);
        service.addCar(car2);
        service.addCar(car3);
        logger.info("✓ 3 cars added successfully");

        // Add user
        logger.info("Adding user...");
        User user = new User("Gaurav", "gaurav@gmail.com", "123456789", "DL123456789", 23);
        service.addUser(user);
        logger.info("✓ User added: " + user.name);

        // Browse available cars
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = LocalDateTime.now().plusDays(1); // Changed from 0 to 1
        logger.info("Browsing available cars for SEDAN from " + startTime + " to " + endTime);

        List<AvailableCarDetails> availableCarDetails = service.browseAvailableCars(startTime, endTime, CarType.SEDAN);
        logger.info("Found " + availableCarDetails.size() + " available cars");
        for (AvailableCarDetails detail : availableCarDetails) {
            logger.info("  - " + detail);
        }

        // Book car
        logger.info("Attempting to book car...");
        BookingResult bookResult = service.bookCar(user, car2, startTime, endTime);
        if (bookResult.success) {
            logger.info("✓ Booking successful: " + bookResult.message);
            logger.info("  Booking ID: " + bookResult.booking.id);
        } else {
            logger.warning("✗ Booking failed: " + bookResult.message);
            return;
        }

        // Pick up car
        logger.info("Attempting to pick up car...");
        BookingResult pickupResult = service.pickUp(bookResult.booking.id);
        if (pickupResult.success) {
            logger.info("✓ Pickup successful: " + pickupResult.message);
        } else {
            logger.warning("✗ Pickup failed: " + pickupResult.message);
        }

        // Return car
        logger.info("Attempting to return car...");
        BookingResult returnResult = service.returnCar(pickupResult.booking.id);
        if (returnResult.success) {
            logger.info("✓ Return successful: " + returnResult.message);
        } else {
            logger.warning("✗ Return failed: " + returnResult.message);
        }

        logger.info("===== Car Rental System Completed =====");
    }
}