package com.busguidance.repository;

import com.busguidance.model.Driver;
import com.busguidance.validation.DriverValidator;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
/**
 * Handles all file-based persistence operations for Driver records.
 * Provides CRUD operations (add, retrieve, update, count, getAll)
 * backed by a plain-text flat file.
 */

public class DriverRepository {

    private static final String FILE_PATH = "data/drivers.txt";
    /**
     * Adds a new driver to the data file after validating their details.
     * Validates driver ID, address, and birthdate before writing.
     * Also checks for duplicate driver IDs.
     *
     * driver The Driver object to be added
     * @return true if the driver was successfully added, false if validation fails or duplicate exists
     */

    public boolean add(Driver driver) {

        if (!DriverValidator.isValidDriverID(driver.getDriverID())) {
            return false;
        }

        if (!DriverValidator.isValidAddress(driver.getAddress())) {
            return false;
        }

        if (!DriverValidator.isValidBirthdate(driver.getBirthdate())) {
            return false;
        }

        if (retrieve(driver.getDriverID()) != null) {
            return false;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(driver.toString());
            writer.newLine();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public Driver retrieve(String driverID) {

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {

            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts[0].equals(driverID)) {
                    return new Driver(
                            parts[0],
                            parts[1],
                            Integer.parseInt(parts[2]),
                            parts[3],
                            parts[4],
                            parts[5]
                    );
                }
            }

        } catch (IOException e) {
            return null;
        }

        return null;
    }

    public boolean update(String driverID, Driver updatedDriver) {

        List<Driver> drivers = getAllDrivers();
        boolean found = false;

        for (int i = 0; i < drivers.size(); i++) {

            Driver existingDriver = drivers.get(i);

            if (existingDriver.getDriverID().equals(driverID)) {

                // Driver ID and name cannot be changed.
                if (!existingDriver.getDriverID().equals(updatedDriver.getDriverID()) ||
                        !existingDriver.getName().equals(updatedDriver.getName())) {
                    return false;
                }

                // If experience is more than 10 years, license type cannot be changed.
                if (existingDriver.getExperienceYears() > 10 &&
                        !existingDriver.getLicenseType().equals(updatedDriver.getLicenseType())) {
                    return false;
                }

                drivers.set(i, updatedDriver);
                found = true;
                break;
            }
        }

        if (!found) {
            return false;
        }

        return rewriteFile(drivers);
    }

    public int count() {

        int count = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            while (reader.readLine() != null) {
                count++;
            }
        } catch (IOException e) {
            return 0;
        }

        return count;
    }
    /**
     * Reads all driver records from the file and returns them as a list.
     * Each line is parsed into a Driver object by splitting on commas.
     *
     * @return A List of all Driver objects found in the file; empty list if file is unreadable
     */


    public List<Driver> getAllDrivers() {

        List<Driver> drivers = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {

            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                drivers.add(
                        new Driver(
                                parts[0],
                                parts[1],
                                Integer.parseInt(parts[2]),
                                parts[3],
                                parts[4],
                                parts[5]
                        )
                );
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return drivers;
    }
     /**
     * Overwrites the data file with the provided list of drivers.
     * Used internally by update() to persist changes after modifying the in-memory list.
     *
     * @param drivers The updated list of Driver objects to write to file
     * @return true if the file was successfully rewritten, false if an IOException occurred
     */

    private boolean rewriteFile(List<Driver> drivers) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {

            for (Driver driver : drivers) {
                writer.write(driver.toString());
                writer.newLine();
            }

            return true;

        } catch (IOException e) {
            return false;
        }
    }
}