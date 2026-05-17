package CarRentalSystemPractice;

public enum CarType {
    SUV("Suv"),
    SEDAN("Sedan"),
    MICRO("Micro"),
    HATCHBACK("hatchback"),
    OFF_ROAD("Off-road")
    ;
    private final String type;
    CarType(String type) {
        this.type = type;
    }

    String getCarType(){
        return this.type;
    }
}
