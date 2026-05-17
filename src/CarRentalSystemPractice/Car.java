package CarRentalSystemPractice;

import java.util.Objects;
import java.util.UUID;

public class Car {
    String licence_plate;
    String id;
    String name;
    String model;
    CarType carType;
    CarStatus status;
    double KilometersDriven;

    public Car(String licence_plate,CarType carType,String name){
        this.licence_plate = licence_plate;
        this.id = UUID.randomUUID().toString();
        this.carType = carType;
        this.name = name;
        this.status = CarStatus.AVAILABLE;
        this.KilometersDriven  = 0.0;
    }

    public void updateStatus(CarStatus status){
        this.status = status;
    }

    boolean isAvailable(){
        return this.status == CarStatus.AVAILABLE;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Double.compare(KilometersDriven, car.KilometersDriven) == 0 && Objects.equals(licence_plate, car.licence_plate) && Objects.equals(id, car.id) && Objects.equals(name, car.name) && Objects.equals(model, car.model) && carType == car.carType && status == car.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Car{" +
                "licence_plate='" + licence_plate + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", model='" + model + '\'' +
                ", carType=" + carType +
                ", status=" + status +
                ", KilometersDriven=" + KilometersDriven +
                '}';
    }
}
