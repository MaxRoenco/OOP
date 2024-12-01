package CarServiceLab.AboutCars;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Car {
    private int id;
    private String type;
    private String passengers;
    @JsonProperty("isDining")
    private boolean isDining;
    private int consumption;

    public Car() {
    }

    public Car(int id, String type, String passengers, boolean isDining, int consumption) {
        this.id = id;
        this.type = type;
        this.passengers = passengers;
        this.isDining = isDining;
        this.consumption = consumption;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPassengers() {
        return passengers;
    }

    public void setPassengers(String passengers) {
        this.passengers = passengers;
    }

    public boolean isDining() {
        return isDining;
    }

    public void setDining(boolean dining) {
        isDining = dining;
    }

    public int getConsumption() {
        return consumption;
    }

    public void setConsumption(int consumption) {
        this.consumption = consumption;
    }

    @Override
    public String toString() {
        return String.format("Car{id=%d, type='%s', passengers='%s', isDining=%b, consumption=%d}",
                id, type, passengers, isDining, consumption);
    }
}
