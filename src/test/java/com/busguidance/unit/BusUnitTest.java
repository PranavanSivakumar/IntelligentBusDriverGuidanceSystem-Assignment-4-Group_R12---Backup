package com.busguidance.unit;

import com.busguidance.model.Bus;
import com.busguidance.model.Driver;
import com.busguidance.validation.BusValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BusUnitTest {

    @Test
    public void B1_TC01_validBusIDShouldReturnTrue() {
        assertTrue(BusValidator.isValidBusID("12345678"));
    }

    @Test
    public void B1_TC02_busIDWithLettersShouldReturnFalse() {
        assertFalse(BusValidator.isValidBusID("1234ABCD"));
    }

    @Test
    public void B1_TC03_busIDLessThan8DigitsShouldReturnFalse() {
        assertFalse(BusValidator.isValidBusID("1234567"));
    }

    @Test
    public void B2_TC01_capacityDecreaseShouldBeAllowed() {
        assertTrue(BusValidator.canUpdateCapacity(60, 50));
    }

    @Test
    public void B2_TC02_capacitySameShouldBeAllowed() {
        assertTrue(BusValidator.canUpdateCapacity(50, 50));
    }

    @Test
    public void B2_TC03_capacityIncreaseShouldNotBeAllowed() {
        assertFalse(BusValidator.canUpdateCapacity(40, 50));
    }

    @Test
    public void B3_TC01_driverOver50CannotDriveLargeBus() {
        Driver driver = new Driver(
                "23@@abcdAB",
                "John Smith",
                20,
                "Heavy",
                "12|King Street|Melbourne|VIC|Australia",
                "01-01-1970"
        );

        Bus bus = new Bus("12345678", 50, 90.0, "Diesel");

        assertFalse(BusValidator.canDriverOperateBus(driver, bus));
    }

    @Test
    public void B3_TC02_driverOver50CanDriveSmallBus() {
        Driver driver = new Driver(
                "24@@abcdAB",
                "Alex Brown",
                20,
                "Heavy",
                "12|King Street|Melbourne|VIC|Australia",
                "01-01-1970"
        );

        Bus bus = new Bus("12345679", 40, 90.0, "Diesel");

        assertTrue(BusValidator.canDriverOperateBus(driver, bus));
    }

    @Test
    public void B3_TC03_driverUnder50CanDriveLargeBus() {
        Driver driver = new Driver(
                "25@@abcdAB",
                "Sam Wilson",
                10,
                "Heavy",
                "12|King Street|Melbourne|VIC|Australia",
                "01-01-1990"
        );

        Bus bus = new Bus("12345680", 50, 90.0, "Diesel");

        assertTrue(BusValidator.canDriverOperateBus(driver, bus));
    }

    @Test
    public void B4_TC01_driverWithFiveYearsCanDriveElectricBus() {
        Driver driver = new Driver(
                "26@@abcdAB",
                "David Lee",
                5,
                "Heavy",
                "12|King Street|Melbourne|VIC|Australia",
                "01-01-1990"
        );

        Bus bus = new Bus("12345681", 40, 90.0, "Electricity");

        assertTrue(BusValidator.canDriverOperateBus(driver, bus));
    }

    @Test
    public void B4_TC02_driverWithLessThanFiveYearsCannotDriveElectricBus() {
        Driver driver = new Driver(
                "27@@abcdAB",
                "Chris Green",
                4,
                "Heavy",
                "12|King Street|Melbourne|VIC|Australia",
                "01-01-1990"
        );

        Bus bus = new Bus("12345682", 40, 90.0, "Electricity");

        assertFalse(BusValidator.canDriverOperateBus(driver, bus));
    }

    @Test
    public void B4_TC03_driverWithMoreThanFiveYearsCanDriveElectricBus() {
        Driver driver = new Driver(
                "28@@abcdAB",
                "Michael White",
                8,
                "Heavy",
                "12|King Street|Melbourne|VIC|Australia",
                "01-01-1990"
        );

        Bus bus = new Bus("12345683", 40, 90.0, "Electricity");

        assertTrue(BusValidator.canDriverOperateBus(driver, bus));
    }

    @Test
    public void B5_TC01_heavyLicenseCanOperateHybridBus() {
        Driver driver = new Driver(
                "29@@abcdAB",
                "Robert King",
                10,
                "Heavy",
                "12|King Street|Melbourne|VIC|Australia",
                "01-01-1990"
        );

        Bus bus = new Bus("12345684", 40, 90.0, "Hybrid");

        assertTrue(BusValidator.canDriverOperateBus(driver, bus));
    }

    @Test
    public void B5_TC02_publicTransportLicenseCanOperateElectricBus() {
        Driver driver = new Driver(
                "30@@abcdAB",
                "Daniel Scott",
                10,
                "PublicTransport",
                "12|King Street|Melbourne|VIC|Australia",
                "01-01-1990"
        );

        Bus bus = new Bus("12345685", 40, 90.0, "Electricity");

        assertTrue(BusValidator.canDriverOperateBus(driver, bus));
    }

    @Test
    public void B5_TC03_lightLicenseCannotOperateHybridBus() {
        Driver driver = new Driver(
                "31@@abcdAB",
                "Peter Young",
                10,
                "Light",
                "12|King Street|Melbourne|VIC|Australia",
                "01-01-1990"
        );

        Bus bus = new Bus("12345686", 40, 90.0, "Hybrid");

        assertFalse(BusValidator.canDriverOperateBus(driver, bus));
    }
}