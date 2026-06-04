package com.busguidance.validation;

import com.busguidance.model.Bus;
import com.busguidance.model.Driver;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class BusValidator {

    // Validates that the bus ID contains exactly 8 digits
    public static boolean isValidBusID(String busID) {

        if (busID == null) {
            return false;
        }

        return busID.matches("\\d{8}");
    }

    // Checks whether a bus capacity update is allowed
    // Capacity can stay the same or decrease, but cannot increase
    public static boolean canUpdateCapacity(int oldCapacity, int newCapacity) {
        return newCapacity <= oldCapacity;
    }

    // Validates whether a driver is eligible to operate a specific bus
    public static boolean canDriverOperateBus(Driver driver, Bus bus) {

        int age = calculateAge(driver.getBirthdate());

        // Drivers over 50 cannot operate buses with capacity 50 or greater
        if (age > 50 && bus.getCapacity() >= 50) {
            return false;
        }

        // Electric buses require at least 5 years of driving experience
        if (bus.getFuelType().equalsIgnoreCase("Electricity") &&
                driver.getExperienceYears() < 5) {
            return false;
        }

        // Electric and Hybrid buses require Heavy or Public Transport licences
        if (bus.getFuelType().equalsIgnoreCase("Electricity") ||
                bus.getFuelType().equalsIgnoreCase("Hybrid")) {

            return driver.getLicenseType().equalsIgnoreCase("Heavy") ||
                    driver.getLicenseType().equalsIgnoreCase("PublicTransport");
        }

        return true;
    }

    // Calculates driver's age from birthdate
    public static int calculateAge(String birthdate) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate dob = LocalDate.parse(birthdate, formatter);

        return Period.between(dob, LocalDate.now()).getYears();
    }
}