import java.security.SecureRandom;

/**
 * A class dedicated to the inner operations of the cash register, and storing
 * it's values of currency inside a private map.
 */
public class Register {

    private int[] currencyArray = new int[8];
    private String[] valueTitleArray = {"Pennies:", "Nickels:", "Dimes:",
            "Quarters:", "Single:", "Fives:", "Tens:", "Twenties:"};
    private int[] multiplyArray = {1, 5, 10, 25, 100, 500, 1000, 2000};
    private int totalCents = 0;

    /**
     * Initializes values in cents for each of the cash units at random (0x-15x).
     */
    public Register() {
        SecureRandom randomNumbers = new SecureRandom();
        int cents;
        for (int i = 0; i < 8; i++) {
            cents = randomNumbers.nextInt(16) * multiplyArray[i];
            totalCents += cents;
            currencyArray[i] = cents;
        }
    }


    /**
     * Prints all cash values by iterating through the private register currencyArray
     */
    public void printTotals() {
        System.out.println("Current Values:\n" + "*****************************");
        int cents;
        for (int i = 0; i < 8; i++) {
            cents = currencyArray[i];
            String dollarAmount = centsAsString(cents);
            // https://docs.oracle.com/javase/7/docs/api/java/util/Formatter.html#syntax
            // %[argument_index$][flags][width][.precision]conversion
            System.out.print(String.format("%-12s %13s %n", valueTitleArray[i], dollarAmount));
        }
        System.out.println("*****************************");
        System.out.print(String.format("%-12s %13s %n", "Total:", centsAsString(totalCents)));
        System.out.println("*****************************");
    }

    /**
     * Prints all the cash values of owed change array given in parameter.
     *
     * @param owedArray an array of cents organized by value type to return to the customer
     */
    public void printOwedArray(int[] owedArray) {
        System.out.println("\nYou owe the customer:\n" + "*****************************");
        int cents;
        int totalOwed = 0;
        for (int i = 0; i < 8; i++) {
            cents = owedArray[i];
            totalOwed += cents;
            String dollarAmount = centsAsString(cents);
            // https://docs.oracle.com/javase/7/docs/api/java/util/Formatter.html#syntax
            // %[argument_index$][flags][width][.precision]conversion
            System.out.print(String.format("%-12s %13s %n", valueTitleArray[i], dollarAmount));
        }
        System.out.println("*****************************");
        System.out.print(String.format("%-12s %13s %n", "Total Owed:", centsAsString(totalOwed)));
        System.out.println("*****************************");
    }

    /**
     * @param cents integer amount of cents that a currency represents in the register.
     * @return a formatted dollar amount string as interpretation of given cents.
     */
    private String centsAsString(int cents) {
        String dollarAmount;
        if (cents < 10) {
            dollarAmount = "$0.0" + Integer.toString(cents);
        } else if (cents < 100) {
            dollarAmount = "$0." + Integer.toString(cents);
        } else {
            dollarAmount = '$' + Integer.toString(cents);
            dollarAmount = dollarAmount.substring(0, dollarAmount.length() - 2) +
                    '.' + dollarAmount.substring(dollarAmount.length() - 2, dollarAmount.length());
        }
        return dollarAmount;
    }

    public void chargeCustomer(int centsCharged, int centsPaid) {
        int owedCents = centsPaid - centsCharged;
        if (owedCents < 0) {
            System.out.println("*****************************");
            System.out.println("The customer paid less than what's due, please try again.");
            System.out.print("Press Enter to dismiss:");
        } else if (owedCents > totalCents) {
            System.out.println("*****************************");
            System.out.println("Sorry, but the register doesn't have enough " +
                    "money to process this charge at the moment.");
            System.out.print("Press Enter to dismiss:");
        } else if (owedCents == 0) {
            System.out.println("*****************************");
            System.out.println("The customer paid with exact change. Nice.");
            System.out.print("Press Enter to dismiss:");
            manualTransaction(true, centsPaid);
        } else {
            int[] backupCurrencyArray = currencyArray;
            int[] owedCentsArray = {0, 0, 0, 0, 0, 0, 0, 0};
            for (int i = 7; i > -1; i--) {
                while ((owedCents >= multiplyArray[i]) && (currencyArray[i] != 0)) {
                    owedCentsArray[i] += multiplyArray[i];
                    currencyArray[i] -= multiplyArray[i];
                    owedCents -= multiplyArray[i];
                }
            }
            if (owedCents == 0) {
                manualTransaction(true, centsPaid);
                printOwedArray(owedCentsArray);
                // Print the array by adding a parameter to the print method above. set it's default to currencyArray tho.
            } else {
                currencyArray = backupCurrencyArray;
                System.out.println("Register was unable to give proper change," +
                        "apologizes for the inconvenience.");
                System.out.print("Press Enter to dismiss:");
            }
        }
        // ?: How to try processing the payment without affecting the register.
        // A: Save a copy of the currency Values to reassign if needed
        // manualTransaction of the payment?

    }

    public void manualTransaction(boolean pos, int cents) {
        int[] backupCurrencyArray = currencyArray;
        if (cents > totalCents && !pos) {
            System.out.println("Sorry, but the register is short " +
                    (cents - totalCents) + " cents of doing that transaction.");
            System.out.print("Press Enter to dismiss:");
        }
        else {
            int chargedCents = cents;
            for (int i = 7; i > -1; i--) {
                while (cents >= multiplyArray[i] && currencyArray[i] != 0) {
                    if (pos) {
                        currencyArray[i] += multiplyArray[i];
                    } else {
                        currencyArray[i] -= multiplyArray[i];
                    }
                    cents -= multiplyArray[i];
                }
            }
        }
    }

}
