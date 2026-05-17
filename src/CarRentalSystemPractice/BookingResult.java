package CarRentalSystemPractice;

public class BookingResult {
    boolean success;
    String message;
    Booking booking;

    public BookingResult(boolean success, String message, Booking booking) {
        this.success = success;
        this.message = message;
        this.booking = booking;
    }
    public BookingResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    @Override
    public String toString() {
        return "BookingResult{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", booking=" + (booking != null ? booking.id : "null") +
                '}';
    }
}
