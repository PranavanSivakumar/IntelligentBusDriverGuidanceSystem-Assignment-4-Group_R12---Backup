package com.busguidance.integration;

import com.busguidance.model.Driver;
import com.busguidance.repository.DriverRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class DriverIntegrationTest {

    private DriverRepository repository;

    @BeforeEach
    public void setup() throws IOException {
        repository = new DriverRepository();

        // Clear the real TXT file before each integration test
        FileWriter writer = new FileWriter("data/drivers.txt");
        writer.write("");
        writer.close();
    }

    @Test
    public void IT_DRIVER_01_validDriverStoredCorrectly() {
        Driver driver = new Driver(
                "23@@abcdAB",
                "John Smith",
                5,
                "Heavy",
                "12|King Street|Melbourne|VIC|Australia",
                "24-11-1995"
        );

        assertTrue(repository.add(driver));

        Driver retrievedDriver = repository.retrieve("23@@abcdAB");

        assertNotNull(retrievedDriver);
        assertEquals("John Smith", retrievedDriver.getName());
        assertEquals("Heavy", retrievedDriver.getLicenseType());
    }

    @Test
    public void IT_DRIVER_02_invalidDriverRejected() {
        Driver invalidDriver = new Driver(
                "10@@abcdAB",
                "Invalid Driver",
                5,
                "Heavy",
                "12|King Street|Melbourne|VIC|Australia",
                "24-11-1995"
        );

        assertFalse(repository.add(invalidDriver));
        assertEquals(0, repository.count());
    }

    @Test
    public void IT_DRIVER_03_updatePersistedCorrectly() {
        Driver driver = new Driver(
                "24@@abcdAB",
                "Alex Brown",
                5,
                "Medium",
                "15|Queen Street|Melbourne|VIC|Australia",
                "10-05-1995"
        );

        repository.add(driver);

        Driver updatedDriver = new Driver(
                "24@@abcdAB",
                "Alex Brown",
                5,
                "Heavy",
                "20|Queen Street|Melbourne|VIC|Australia",
                "10-05-1995"
        );

        assertTrue(repository.update("24@@abcdAB", updatedDriver));

        Driver retrievedDriver = repository.retrieve("24@@abcdAB");

        assertNotNull(retrievedDriver);
        assertEquals("Heavy", retrievedDriver.getLicenseType());
        assertEquals("20|Queen Street|Melbourne|VIC|Australia", retrievedDriver.getAddress());
    }

    @Test
    public void IT_DRIVER_04_recordCountUpdatedCorrectly() {
        Driver driver1 = new Driver(
                "25@@abcdAB",
                "Sam Wilson",
                6,
                "Heavy",
                "20|Collins Street|Melbourne|VIC|Australia",
                "15-08-1990"
        );

        Driver driver2 = new Driver(
                "26@@abcdAB",
                "David Lee",
                7,
                "PublicTransport",
                "30|Bourke Street|Melbourne|VIC|Australia",
                "01-01-1989"
        );

        assertTrue(repository.add(driver1));
        assertTrue(repository.add(driver2));

        assertEquals(2, repository.count());
    }
}