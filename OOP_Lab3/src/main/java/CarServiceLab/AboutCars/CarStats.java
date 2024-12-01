package CarServiceLab.AboutCars;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.LinkedHashMap;
import java.util.Map;

public class CarStats {
    private final Map<String, Integer> typeStats = new LinkedHashMap<>();
    private final Map<String, Integer> passengerStats = new LinkedHashMap<>();
    private final Map<String, Integer> diningStats = new LinkedHashMap<>();
    private final Map<String, Integer> consumption = new LinkedHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public CarStats() {
        diningStats.put("DINING", 0);
        diningStats.put("NOT_DINING", 0);
    }

    public synchronized void updateStats(Car car) {
        typeStats.merge(car.getType(), 1, Integer::sum);

        passengerStats.merge(car.getPassengers(), 1, Integer::sum);

        String diningKey = car.isDining() ? "DINING" : "NOT_DINING";
        diningStats.put(diningKey, diningStats.get(diningKey) + 1);

        consumption.merge(car.getType(), car.getConsumption(), Integer::sum);
    }

    public void printStats() {
        try {
            Map<String, Object> finalStats = new LinkedHashMap<>();
            finalStats.put("TYPES", typeStats);
            finalStats.put("PASSENGERS", passengerStats);
            finalStats.put("DINING", diningStats);
            finalStats.put("CONSUMPTION", consumption);

            String jsonStats = objectMapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(finalStats);
            System.out.println(jsonStats);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public int getElectricConsumption() {
        return consumption.getOrDefault("ELECTRIC", 0);
    }

    public int getGasConsumption() {
        return consumption.getOrDefault("GAS", 0);
    }

    public int getElectricCount() {
        return typeStats.getOrDefault("ELECTRIC", 0);
    }

    public int getGasCount() {
        return typeStats.getOrDefault("GAS", 0);
    }

    public int getPeopleCount(){
        return passengerStats.getOrDefault("PEOPLE", 0);
    }

    public int getRobotCount(){
        return passengerStats.getOrDefault("ROBOTS", 0);
    }
    public int getDiningCount(){
        return diningStats.getOrDefault("DINING", 0);
    }
    public int getNotDiningCount(){
        return diningStats.getOrDefault("NOT_DINING", 0);
    }
}