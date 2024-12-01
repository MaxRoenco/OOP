package CarServiceLab;

import CarServiceLab.AboutCars.Car;
import CarServiceLab.AboutCars.CarStats;
import CarServiceLab.Serving.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CarServiceTest {
    private CarServiceSemaphore semaphore;

    @Before
    public void setup() {
        semaphore = new CarServiceSemaphore();
    }

    @Test
    public void testElectricPeopleCar() throws InterruptedException {
        Car car = new Car(1, "ELECTRIC", "PEOPLE", false, 42);
        semaphore.routeCar(car);

        CarStats stats = semaphore.getStats();
        assertEquals(1, stats.getElectricCount());
        assertEquals(1, stats.getPeopleCount());
        assertEquals(42, stats.getElectricConsumption());
    }

    @Test
    public void testGasRobotCar() throws InterruptedException {
        Car car = new Car(2, "GAS", "ROBOTS", true, 30);
        semaphore.routeCar(car);

        CarStats stats = semaphore.getStats();
        assertEquals(1, stats.getGasCount());
        assertEquals(1, stats.getRobotCount());
        assertEquals(30, stats.getGasConsumption());
        assertEquals(1, stats.getDiningCount());
    }

    @Test
    public void testMultipleCars() throws InterruptedException {
        Car[] cars = {
                new Car(1, "ELECTRIC", "PEOPLE", false, 42),
                new Car(2, "GAS", "ROBOTS", true, 30),
                new Car(3, "ELECTRIC", "ROBOTS", true, 25),
                new Car(4, "GAS", "PEOPLE", false, 35)
        };

        for (Car car : cars) {
            semaphore.routeCar(car);
        }

        CarStats stats = semaphore.getStats();
        assertEquals(2, stats.getElectricCount());
        assertEquals(2, stats.getGasCount());
        assertEquals(2, stats.getPeopleCount());
        assertEquals(2, stats.getRobotCount());
        assertEquals(67, stats.getElectricConsumption());
        assertEquals(65, stats.getGasConsumption());
        assertEquals(2, stats.getDiningCount());
        assertEquals(2, stats.getNotDiningCount());
    }

    @Test
    public void testJsonInput() throws InterruptedException, JsonProcessingException {
        String[] jsonInputs = {
                "{\"id\": 1, \"type\": \"ELECTRIC\", \"passengers\": \"PEOPLE\", \"isDining\": false, \"consumption\": 42}",
                "{\"id\": 2, \"type\": \"ELECTRIC\", \"passengers\": \"PEOPLE\", \"isDining\": false, \"consumption\": 26}",
                "{\"id\": 3, \"type\": \"GAS\", \"passengers\": \"ROBOTS\", \"isDining\": true, \"consumption\": 41}"
        };

        ObjectMapper mapper = new ObjectMapper();
        for (String json : jsonInputs) {
            Car car = mapper.readValue(json, Car.class);
            semaphore.routeCar(car);
        }

        CarStats stats = semaphore.getStats();
        assertEquals(2, stats.getElectricCount());
        assertEquals(1, stats.getGasCount());
        assertEquals(2, stats.getPeopleCount());
        assertEquals(1, stats.getRobotCount());
        assertEquals(68, stats.getElectricConsumption());
        assertEquals(41, stats.getGasConsumption());
        assertEquals(1, stats.getDiningCount());
        assertEquals(2, stats.getNotDiningCount());
    }
}
