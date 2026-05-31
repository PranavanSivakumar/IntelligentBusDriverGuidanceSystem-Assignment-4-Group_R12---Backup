package com.busguidance.integration;

import com.busguidance.model.Bus;
import com.busguidance.repository.BusRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class BusIntegrationTest {

    private BusRepository repository;

    @BeforeEach
    public void setup() throws IOException {
        repository = new BusRepository();

        // Clear the real TXT file before each integration test
        FileWriter writer = new FileWriter("data/buses.txt");
        writer.write("");
        writer.close();
    }

    @Test
    public void IT_BUS_01_validBusStoredCorrectly() {
        Bus bus = new Bus("12345678", 40, 90.0, "Diesel");

        assertTrue(repository.add(bus));

        Bus retrievedBus = repository.retrieve("12345678");

        assertNotNull(retrievedBus);
        assertEquals(40, retrievedBus.getCapacity());
        assertEquals(90.0, retrievedBus.getFuelLevel());
        assertEquals("Diesel", retrievedBus.getFuelType());
    }

    @Test
    public void IT_BUS_02_invalidBusRejected() {
        Bus invalidBus = new Bus("1234ABCD", 40, 90.0, "Diesel");

        assertFalse(repository.add(invalidBus));
        assertEquals(0, repository.count());
    }

    @Test
    public void IT_BUS_03_updatePersistedCorrectly() {
        Bus bus = new Bus("12345679", 60, 80.0, "Diesel");

        repository.add(bus);

        Bus updatedBus = new Bus("12345679", 50, 70.0, "Hybrid");

        assertTrue(repository.update("12345679", updatedBus));

        Bus retrievedBus = repository.retrieve("12345679");

        assertNotNull(retrievedBus);
        assertEquals(50, retrievedBus.getCapacity());
        assertEquals(70.0, retrievedBus.getFuelLevel());
        assertEquals("Hybrid", retrievedBus.getFuelType());
    }

    @Test
    public void IT_BUS_04_recordCountUpdatedCorrectly() {
        Bus bus1 = new Bus("12345680", 40, 90.0, "Diesel");
        Bus bus2 = new Bus("12345681", 45, 85.0, "Hybrid");

        assertTrue(repository.add(bus1));
        assertTrue(repository.add(bus2));

        assertEquals(2, repository.count());
    }
}