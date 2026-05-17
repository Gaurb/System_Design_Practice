package CarRentalSystemPractice;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CarRentalService {
    List<Booking> bookings;
    List<Car> cars;
    List<User> users;
    PricingStrategy pricingStrategy;
    Map<String, Lock> carLocks; // For concurrency control on car bookings
    Lock globalLock; // For operations that require global consistency (e.g., adding cars/users)

    public CarRentalService() {
        this.cars = new ArrayList<>();
        this.users = new ArrayList<>();
        this.pricingStrategy = new StandardPricing();
        this.carLocks = new ConcurrentHashMap<>();
        globalLock = new ReentrantLock();
        this.bookings  = new ArrayList<>();
    }

    public Lock getCarLock(String carId) {
        carLocks.putIfAbsent(carId, new ReentrantLock());
        return carLocks.get(carId);
    }

    public void addCar(Car car){
        cars.add(car);
    }

    public void addUser(User user) {
        users.add(user);
    }

    List<AvailableCarDetails> browseAvailableCars(LocalDateTime startTime,LocalDateTime endTime,CarType carType){
        List<AvailableCarDetails> availableCarDetails = new ArrayList<>();
        int hours =(int) Duration.between(startTime,endTime).toHours();

        for(Car car: cars){
            if(car.carType != carType) continue;
            if(isCarAvailable(car,startTime,endTime)){
                double price = pricingStrategy.calculatePrice(carType,hours);
                availableCarDetails.add(
                        new AvailableCarDetails(car,price,price/hours)
                );
            }
        }

        return availableCarDetails;
    }

    boolean isCarAvailable(Car car,LocalDateTime startTime,LocalDateTime endTime){
        if(car.status == CarStatus.MAINTENANCE) return false;
        for(Booking booking: bookings){
            if(booking.car.equals(car)) continue;
            if(booking.status != BookingStatus.CONFIRMED || booking.status != BookingStatus.ACTIVE) continue;
            if (booking.endTime.isAfter(startTime) || booking.startTime.isAfter(endTime)) return false;
        }
        return true;
    }

    BookingResult bookCar(User user,Car car,LocalDateTime startTime,LocalDateTime endTime){
        if(!user.isEligibleToRent()) return new BookingResult(false,"User not eligible to rent");

        Lock lock = getCarLock(car.id);
        lock.lock();
        try {
            if(!isCarAvailable(car,startTime,endTime))
                return new BookingResult(false,"Car not available for selected dates");

            Booking booking = new  Booking(user,car,startTime,endTime);

            int hours = booking.getDurationHours();
            double amount = pricingStrategy.calculatePrice(car.carType,hours);
            Payment payment = new Payment(user,amount);

            payment.completePayment("TXN_"+booking.id.substring(4));
            booking.payment = payment;

            bookings.add(booking);
            return new BookingResult(true,"Booking Confirmed! Amount: "+ amount+" Rs",booking);
        }finally {
            lock.unlock();
        }
    }

    BookingResult pickUp(String bookingId){
        Booking booking = getBooking(bookingId);
        if(booking == null){
            return new BookingResult(false,"Booking not found");
        }

        Lock carLock = getCarLock(booking.car.id);
        carLock.lock();
        try{
            if(!booking.status.equals(BookingStatus.CONFIRMED)){
                return new BookingResult(false,"Invalid Status: " + booking.status);
            }
            if(!booking.payment.status.equals(PaymentStatus.COMPLETE)){
                return new BookingResult(false,"Payment is not completed");
            }

            booking.status = BookingStatus.ACTIVE;
            booking.car.status = CarStatus.RENTED;

            return new BookingResult(true,"Car picked up successfully",booking);
        }finally {
            carLock.unlock();
        }
    }

    BookingResult returnCar(String bookingId){
        Booking booking = getBooking(bookingId);
        if(booking== null){
            return new BookingResult(false,"Booking Not Found");
        }
        Lock carLock = getCarLock(booking.car.id);
        carLock.lock();
        try{
            if(!booking.status.equals(BookingStatus.ACTIVE)){
                return new BookingResult(false,"Invalid Status: "+booking.status);
            }
            booking.actualReturnTime = LocalDateTime.now();
            booking.status = BookingStatus.COMPLETED;
            booking.car.status = CarStatus.AVAILABLE;
            double lateFee = 0;
            if(booking.isLateReturn()){
                int lateHour = booking.getLateHours();
                Integer hourly = pricingStrategy.rates.get(booking.car.carType).get("hourly");
                lateFee = lateHour*lateHour*1.5;
            }
            String message = "Car returned successfully. ";
            if(lateFee>0){
                message += "Late fee: "+ lateFee+" Rs";
            }

            return new BookingResult(true,message,booking);
        }finally {
            carLock.unlock();
        }
    }


    BookingResult cancelBooking(String bookingId){
        Booking booking = getBooking(bookingId);
        if(booking == null){
            return new BookingResult(false,"Booking not found");
        }

        Lock carLock = getCarLock(booking.car.id);
        carLock.lock();

        try{
            if(booking.status.equals(BookingStatus.ACTIVE))
                return new BookingResult(false,"Cannot cancel active rental");
            if(booking.status.equals(BookingStatus.CONFIRMED)){
                return new BookingResult(false,"Cannot cancel "+booking.status);
            }
            int hours =(int) Duration.between(booking.startTime, booking.endTime).toHours();
            int refundPercent;
            if(hours>24){
                refundPercent = 100;
            }else if(hours > 6){
                refundPercent = 50;
            }else{
                refundPercent = 0;
            }

            double refundAmount = booking.payment.amount * refundPercent /100;
            booking.status = BookingStatus.CANCELLED;
            if(refundPercent>0){
                booking.payment.refundPayment();
            }

            return new BookingResult(true,"Booking Cancelled. Refund: "+ refundAmount +" Rs ("+ refundPercent+")%");
        }finally {
            carLock.unlock();
        }
    }

    Booking getBooking(String bookingId){
        for(Booking booking: bookings){
            if(booking.id.equals(bookingId)) return booking;
        }
        return null;
    }
}
