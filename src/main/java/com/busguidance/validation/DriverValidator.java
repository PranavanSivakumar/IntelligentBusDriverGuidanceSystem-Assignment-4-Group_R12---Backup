package com.busguidance.validation;

public class DriverValidator {

    public static boolean isValidDriverID(String driverID) {

        if (driverID == null || driverID.length() != 10) {
            return false;
        }

        if (!Character.isDigit(driverID.charAt(0)) ||
                !Character.isDigit(driverID.charAt(1))) {
            return false;
        }

        int first = Character.getNumericValue(driverID.charAt(0));
        int second = Character.getNumericValue(driverID.charAt(1));

        if (first < 2 || first > 9 || second < 2 || second > 9) {
            return false;
        }

        int specialCount = 0;

        for (int i = 2; i <= 7; i++) {
            char c = driverID.charAt(i);

            if (!Character.isLetterOrDigit(c)) {
                specialCount++;
            }
        }

        if (specialCount < 2) {
            return false;
        }

        return Character.isUpperCase(driverID.charAt(8))
                && Character.isUpperCase(driverID.charAt(9));
    }

    public static boolean isValidAddress(String address) {

        if (address == null) {
            return false;
        }

        String[] parts = address.split("\\|");

        return parts.length == 5;
    }

    public static boolean isValidBirthdate(String birthdate) {

        if (birthdate == null) {
            return false;
        }

        return birthdate.matches("\\d{2}-\\d{2}-\\d{4}");
    }
}