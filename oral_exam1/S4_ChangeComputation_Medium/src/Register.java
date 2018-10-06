import java.security.SecureRandom;
import java.util.Scanner;

/**
 * A class dedicated to the inner operations of the cash register, and storing
 * it's values of currency inside a private array, alongside their titles and
 * cent representation.
 */
public class Register {

    private int[] currencyArray = new int[8];
    private String[] valueTitleArray = {"pennies", "nickels", "dimes",
            "quarters", "single", "fives", "tens", "twenties"};
    private int[] multiplyArray = {1, 5, 10, 25, 100, 500, 1000, 2000};
    private int totalCents = 0;
    Scanner input = new Scanner(System.in);

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
            System.out.print(String.format("%-12s %13s %n", valueTitleArray[i] + ":", dollarAmount));
        }
        System.out.println("*****************************");
        System.out.print(String.format("%-12s %13s %n", "total:", centsAsString(totalCents)));
        System.out.println("*****************************");
    }

    /**
     * Prints all the cash values of an owed change array given in parameters.
     *
     * @param owedArray an array of cents organized by value type to return to the customer
     */
    private void printOwedArray(int[] owedArray) {
        System.out.println("\nYou owe the customer:\n" + "*****************************");
        int cents;
        int totalOwed = 0;
        for (int i = 0; i < 8; i++) {
            cents = owedArray[i];
            totalOwed += cents;
            String dollarAmount = centsAsString(cents);
            System.out.print(String.format("%-12s %13s %n", valueTitleArray[i] + ":", dollarAmount));
        }
        System.out.println("*****************************");
        System.out.print(String.format("%-12s %13s %n", "total owed:", centsAsString(totalOwed)));
        System.out.println("*****************************");
        System.out.println("Press enter to continue:");
        input.nextLine();
    }

    /**
     * Converts a given integer as a properly formatted string.
     *
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

    /**
     * Guides the register operator through charging a customer by
     * utilizing {@link #askForCents()}, and deciding if their requested transaction
     * is possible. If possible, this method then edits the register currency
     * values accordingly.
     *
     * @param centsCharged represents the cost of the item that the customer is
     *                     buying. Used to calculated the cents owed in change.
     */
    public void chargeCustomer(int centsCharged) {
        System.out.println("How many of each currency unit did they give you?");
        int[] givenCents = askForCents();
        int[] backupCurrencyArray = currencyArray;
        int backupTotalCents = totalCents;
        manualTransaction(true, givenCents);
        int owedCents = givenCents[8] - centsCharged;
        if (owedCents < 0) {
            System.out.println("*****************************");
            System.out.println("The customer paid less than what's due, please try again.");
            System.out.print("Press Enter to dismiss:");
            input.nextLine();
        } else if (owedCents > totalCents) {
            System.out.println("*****************************");
            System.out.println("Sorry, but the register doesn't have enough " +
                    "money to process this charge at the moment.");
            System.out.print("Press Enter to dismiss:");
            input.nextLine();
        } else if (owedCents == 0) {
            System.out.println("*****************************");
            System.out.println("The customer paid with exact change. Nice.");
            System.out.print("Press Enter to dismiss:");
            input.nextLine();
            manualTransaction(true, givenCents);
        } else {
            int[] owedCentsArray = {0, 0, 0, 0, 0, 0, 0, 0, 0};
            for (int i = 7; i > -1; i--) {
                while ((owedCents >= multiplyArray[i]) && (currencyArray[i] != 0) && owedCents != 0) {
                    owedCentsArray[i] += multiplyArray[i];
                    currencyArray[i] -= multiplyArray[i];
                    totalCents -= multiplyArray[i];
                    owedCents -= multiplyArray[i];
                }
            }
            if (owedCents == 0) {
                printOwedArray(owedCentsArray);
            } else {
                currencyArray = backupCurrencyArray;
                totalCents = backupTotalCents;
                System.out.println("Register was unable to give proper change," +
                        "apologizes for the inconvenience.");
                System.out.print("Press Enter to dismiss:");
                input.nextLine();
            }
        }

    }

    /**
     * Handles the processing of transactions by changing the register currency
     * array, and total cents held.
     *
     * @param pos            a boolean representing if the transaction is
     *                       positive or negative.
     * @param requestedCents An integer array that represents each currency unit,
     *                       and the quantity of that unit involved in the
     *                       transaction.
     */
    public void manualTransaction(boolean pos, int[] requestedCents) {
        int[] backupCurrencyArray = currencyArray;
        int backupTotalCents = totalCents;
        for (int i = 0; i < 8; i++) {
            if (pos) {
                currencyArray[i] += requestedCents[i];
                totalCents += requestedCents[i];
            } else {
                if (currencyArray[i] < requestedCents[i]) {
                    System.out.println("Sorry, but the register is short " +
                            (requestedCents[i] - currencyArray[i]) + " cents in "
                            + valueTitleArray[i] + " to complete that transaction.");
                    i = 8;
                    currencyArray = backupCurrencyArray;
                    totalCents = backupTotalCents;
                } else {
                    currencyArray[i] -= requestedCents[i];
                    totalCents -= requestedCents[i];
                }
            }
        }
    }

    /**
     * Asks the user how many units of currency they would like
     * for each unit type. It validates that each unit is a positive int.
     *
     * @return an array of how much of each unit the user is interacting with,
     * with the last value of the array saved for a cent total across all units.
     */
    public int[] askForCents() {
        int[] requestedCents = new int[9];
        int cents;
        int totalRequestedCents = 0;
        System.out.println("Enter number of coins/bills.");
        for (int i = 0; i < 8; i++) {
            cents = -1;
            while (cents <= -1) {
                System.out.print("How many " + valueTitleArray[i] + ":");
                if (!input.hasNextInt()) {
                    input.nextLine();
                    System.out.println("Please enter an int value.");
                } else {
                    cents = input.nextInt();
                    input.nextLine();
                    if (cents <= -1) {
                        System.out.println("Please enter a positive value.");
                    }
                }
            }
            requestedCents[i] = cents * multiplyArray[i];
            totalRequestedCents += cents * multiplyArray[i];
        }
        requestedCents[8] = totalRequestedCents;
        return requestedCents;
    }
}
