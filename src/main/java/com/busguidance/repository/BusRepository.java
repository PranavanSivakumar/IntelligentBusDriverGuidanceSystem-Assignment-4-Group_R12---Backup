package com.busguidance.repository;

import com.busguidance.model.Bus;
import com.busguidance.validation.BusValidator;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BusRepository {
    // File used to store bus records

    private static final String FILE_PATH = "data/buses.txt";
    // adds a new bus to the file
    public boolean add(Bus bus) {

        if (!BusValidator.isValidBusID(bus.getBusID())) {
            return false;
        }
        // Prevents duplicate bus IDs
        if (retrieve(bus.getBusID()) != null) {
            return false;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {

            writer.write(bus.toString());
            writer.newLine();

            return true;

        } catch (IOException e) {
            return false;
        }
    }
    // Retrieves a bus using its ID

    public Bus retrieve(String busID) {

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] parts = line.split(",");

                if (parts[0].equals(busID)) {

                    return new Bus(
                            parts[0],
                            Integer.parseInt(parts[1]),
                            Double.parseDouble(parts[2]),
                            parts[3]
                    );
                }
            }

        } catch (IOException e) {
            return null;
        }

        return null;
    }
    // Updates an existing bus record

    public boolean update(String busID, Bus updatedBus) {

        List<Bus> buses = getAllBuses();
        boolean found = false;

        for (int i = 0; i < buses.size(); i++) {

            Bus existingBus = buses.get(i);

            if (existingBus.getBusID().equals(busID)) {

                // Bus ID cannot be changed
                if (!existingBus.getBusID().equals(updatedBus.getBusID())) {
                    return false;
                }

                // Capacity cannot increase
                if (updatedBus.getCapacity() > existingBus.getCapacity()) {
                    return false;
                }

                buses.set(i, updatedBus);
                found = true;
                break;
            }
        }

        if (!found) {
            return false;
        }

        return rewriteFile(buses);
    }
    // Returns total number of buses stored

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
    // Loads all bus records from file

    public List<Bus> getAllBuses() {

        List<Bus> buses = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] parts = line.split(",");

                buses.add(
                        new Bus(
                                parts[0],
                                Integer.parseInt(parts[1]),
                                Double.parseDouble(parts[2]),
                                parts[3]
                        )
                );
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return buses;
    }
    // Rewrites the entire file after updates

    private boolean rewriteFile(List<Bus> buses) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {

            for (Bus bus : buses) {
                writer.write(bus.toString());
                writer.newLine();
            }

            return true;

        } catch (IOException e) {
            return false;
        }
    }
}