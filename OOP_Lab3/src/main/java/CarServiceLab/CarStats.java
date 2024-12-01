package CarServiceLab;

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
        // Update type stats
        typeStats.merge(car.getType(), 1, Integer::sum);

        // Update passenger stats
        passengerStats.merge(car.getPassengers(), 1, Integer::sum);

        // Update dining stats
        String diningKey = car.isDining() ? "DINING" : "NOT_DINING";
        diningStats.put(diningKey, diningStats.get(diningKey) + 1);

        // Update consumption
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
}