package com.busguidance.model;

/**
 * Represents a bus driver in the Intelligent Bus Driver Guidance System.
 */
public class Driver {
    private String driverID;
    private String name;
    private int experienceYears;
    private String licenseType;
    private String address;
    private String birthdate;
    /**
     * Constructs a new Driver with all required details.
     *
     * driverID        Unique identifier for the driver
     * name            Full name of the driver
     * experienceYears Number of years of driving experience
     * licenseType     Type of license the driver holds
     *  address         Residential address of the driver
     *  birthdate       Date of birth of the driver
     */

    public Driver(String driverID, String name, int experienceYears, String licenseType, String address, String birthdate) {
        this.driverID = driverID;
        this.name = name;
        this.experienceYears = experienceYears;
        this.licenseType = licenseType;
        this.address = address;
        this.birthdate = birthdate;
    }
/**
     * Returns the unique identifier of the driver.
     *
     * @return driverID
     */
    public String getDriverID() {
        return driverID;
    }
/**
     * Returns the full name of the driver.
     *
     * @return everything 
     */
    public String getName() {
        return name;
    }

    public int getExperienceYears() {
        return experienceYears;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public String getAddress() {
        return address;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setExperienceYears(int experienceYears) {
        this.experienceYears = experienceYears;
    }

    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    @Override
    public String toString() {
        return driverID + "," + name + "," + experienceYears + "," + licenseType + "," + address + "," + birthdate;
    }
}