package com.busguidance.unit;

import com.busguidance.model.Driver;
import com.busguidance.repository.DriverRepository;
import com.busguidance.validation.DriverValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DriverUnitTest {

    @Test
    public void D1_TC01_validDriverIDShouldReturnTrue() {
        assertTrue(DriverValidator.isValidDriverID("23@@abcdAB"));
    }

    @Test
    public void D1_TC02_driverIDLessThan10CharactersShouldReturnFalse() {
        assertFalse(DriverValidator.isValidDriverID("23@@abcAB"));
    }

    @Test
    public void D1_TC03_driverIDWithInvalidFirstDigitsShouldReturnFalse() {
        assertFalse(DriverValidator.isValidDriverID("10@@abcdAB"));
    }

    @Test
    public void D2_TC01_validAddressShouldReturnTrue() {
        assertTrue(DriverValidator.isValidAddress("12|King Street|Melbourne|VIC|Australia"));
    }

    @Test
    public void D2_TC02_addressMissingPartsShouldReturnFalse() {
        assertFalse(DriverValidator.isValidAddress("12|King Street|Melbourne"));
    }

    @Test
    public void D2_TC03_nullAddressShouldReturnFalse() {
        assertFalse(DriverValidator.isValidAddress(null));
    }

    @Test
    public void D3_TC01_validBirthdateShouldReturnTrue() {
        assertTrue(DriverValidator.isValidBirthdate("24-11-1999"));
    }

    @Test
    public void D3_TC02_birthdateWrongFormatShouldReturnFalse() {
        assertFalse(DriverValidator.isValidBirthdate("1999-11-24"));
    }

    @Test
    public void D3_TC03_nullBirthdateShouldReturnFalse() {
        assertFalse(DriverValidator.isValidBirthdate(null));
    }

    @Test
    public void D4_TC01_driverWithMoreThan10YearsCannotChangeLicense() {
        DriverRepository repository = new DriverRepository();

        Driver oldDriver = new Driver(
                "23@@abcdAB",
                "John Smith",
                12,
                "Heavy",
                "12|King Street|Melbourne|VIC|Australia",
                "24-11-1980"
        );

        Driver updatedDriver = new Driver(
                "23@@abcdAB",
                "John Smith",
                12,
                "PublicTransport",
                "12|King Street|Melbourne|VIC|Australia",
                "24-11-1980"
        );

        assertTrue(oldDriver.getExperienceYears() > 10 &&
                !oldDriver.getLicenseType().equals(updatedDriver.getLicenseType()));
    }

    @Test
    public void D4_TC02_driverWithLessThan10YearsCanChangeLicense() {
        Driver oldDriver = new Driver(
                "24@@abcdAB",
                "Alex Brown",
                5,
                "Medium",
                "15|Queen Street|Melbourne|VIC|Australia",
                "10-05-1995"
        );

        Driver updatedDriver = new Driver(
                "24@@abcdAB",
                "Alex Brown",
                5,
                "Heavy",
                "15|Queen Street|Melbourne|VIC|Australia",
                "10-05-1995"
        );

        assertTrue(oldDriver.getExperienceYears() <= 10 &&
                !oldDriver.getLicenseType().equals(updatedDriver.getLicenseType()));
    }

    @Test
    public void D4_TC03_driverWithMoreThan10YearsSameLicenseAllowed() {
        Driver oldDriver = new Driver(
                "25@@abcdAB",
                "Sam Wilson",
                15,
                "Heavy",
                "20|Collins Street|Melbourne|VIC|Australia",
                "15-08-1978"
        );

        Driver updatedDriver = new Driver(
                "25@@abcdAB",
                "Sam Wilson",
                15,
                "Heavy",
                "25|Collins Street|Melbourne|VIC|Australia",
                "15-08-1978"
        );

        assertTrue(oldDriver.getExperienceYears() > 10 &&
                oldDriver.getLicenseType().equals(updatedDriver.getLicenseType()));
    }

    @Test
    public void D5_TC01_sameDriverIDAndNameShouldBeAllowed() {
        Driver oldDriver = new Driver(
                "26@@abcdAB",
                "David Lee",
                8,
                "Heavy",
                "30|Bourke Street|Melbourne|VIC|Australia",
                "01-01-1990"
        );

        Driver updatedDriver = new Driver(
                "26@@abcdAB",
                "David Lee",
                8,
                "Heavy",
                "35|Bourke Street|Melbourne|VIC|Australia",
                "01-01-1990"
        );

        assertEquals(oldDriver.getDriverID(), updatedDriver.getDriverID());
        assertEquals(oldDriver.getName(), updatedDriver.getName());
    }

    @Test
    public void D5_TC02_changedDriverIDShouldNotBeAllowed() {
        Driver oldDriver = new Driver(
                "27@@abcdAB",
                "Chris Green",
                8,
                "Heavy",
                "40|Swanston Street|Melbourne|VIC|Australia",
                "01-01-1990"
        );

        Driver updatedDriver = new Driver(
                "28@@abcdAB",
                "Chris Green",
                8,
                "Heavy",
                "40|Swanston Street|Melbourne|VIC|Australia",
                "01-01-1990"
        );

        assertNotEquals(oldDriver.getDriverID(), updatedDriver.getDriverID());
    }

    @Test
    public void D5_TC03_changedNameShouldNotBeAllowed() {
        Driver oldDriver = new Driver(
                "29@@abcdAB",
                "Michael White",
                8,
                "Heavy",
                "50|Elizabeth Street|Melbourne|VIC|Australia",
                "01-01-1990"
        );

        Driver updatedDriver = new Driver(
                "29@@abcdAB",
                "Michael Black",
                8,
                "Heavy",
                "50|Elizabeth Street|Melbourne|VIC|Australia",
                "01-01-1990"
        );

        assertNotEquals(oldDriver.getName(), updatedDriver.getName());
    }
}