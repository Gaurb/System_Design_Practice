package CarRentalSystemPractice;

import java.time.LocalDateTime;
import java.util.UUID;

public class Booking {
    String id;
    User user;
    Car car;
    LocalDateTime startTime;
    LocalDateTime endTime;
    LocalDateTime actualReturnTime;
    BookingStatus status;
    Payment payment;
    LocalDateTime createdAt;

    public Booking(User user, Car car, LocalDateTime startTime, LocalDateTime endTime) {
        this.id = UUID.randomUUID().toString();
        this.user = user;
        this.car = car;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = BookingStatus.CONFIRMED;
        this.createdAt = LocalDateTime.now();
    }

    boolean isLateReturn() {
        if (actualReturnTime == null) {
            return false;
        }
        return actualReturnTime.isAfter(endTime);
    }

    int getLateHours(){
        if (!isLateReturn()) {
            return 0;
        }
        return (int) java.time.Duration.between(endTime, actualReturnTime).toHours();
    }

    int getDurationHours(){
        return (int) java.time.Duration.between(startTime, endTime).toHours();
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id='" + id + '\'' +
                ", user=" + user +
                ", car=" + car +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", actualReturnTime=" + actualReturnTime +
                ", status=" + status +
                ", payment=" + payment +
                ", createdAt=" + createdAt +
                '}';
    }
}
