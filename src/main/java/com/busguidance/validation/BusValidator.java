package com.busguidance.validation;

import com.busguidance.model.Bus;
import com.busguidance.model.Driver;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class BusValidator {

    public static boolean isValidBusID(String busID) {

        if (busID == null) {
            return false;
        }

        return busID.matches("\\d{8}");
    }

    public static boolean canUpdateCapacity(int oldCapacity, int newCapacity) {
        return newCapacity <= oldCapacity;
    }

    public static boolean canDriverOperateBus(Driver driver, Bus bus) {

        int age = calculateAge(driver.getBirthdate());

        if (age > 50 && bus.getCapacity() >= 50) {
            return false;
        }

        if (bus.getFuelType().equalsIgnoreCase("Electricity") &&
                driver.getExperienceYears() < 5) {
            return false;
        }

        if (bus.getFuelType().equalsIgnoreCase("Electricity") ||
                bus.getFuelType().equalsIgnoreCase("Hybrid")) {

            return driver.getLicenseType().equalsIgnoreCase("Heavy") ||
                    driver.getLicenseType().equalsIgnoreCase("PublicTransport");
        }

        return true;
    }

    public static int calculateAge(String birthdate) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate dob = LocalDate.parse(birthdate, formatter);

        return Period.between(dob, LocalDate.now()).getYears();
    }
}