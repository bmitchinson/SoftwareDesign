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
    private int total = 0;

    /**
     * Initializes values in cents for each of the cash units at random (0x-15x).
     */
    public Register() {
        SecureRandom randomNumbers = new SecureRandom();
        int cents;
        for (int i = 0; i < 8; i++) {
            cents = randomNumbers.nextInt(16) * multiplyArray[i];
            total += cents;
            currencyArray[i] = cents;
        }
    }

    /**
     * Prints all cash values by iterating through the currencyArray
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
        System.out.print(String.format("%-12s %13s %n", "Total:", centsAsString(total)));
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
        // If charged is less then paid, need to pay more
        // if paid - charged is less then total, not enough cash in reg
        // ?: How to try processing the payment without affecting the register.
        // A: Save a copy of the currency Values to reassign if needed
        // manualTransaction of the payment?

    }

    public void manualTransaction() {
        int[] potentialCharge = new int[8];
        for (int i = 0; i < 8; i++) { // Iterate through currency array, not 8
            // if
        }
        // TODO: Complete Function
        /* Charge or Credit?
         *  Asks for charge/credit cost, stores as String
         *  Call charge or credit func? Cause below is repeated code.
         *  Parses int and separates by decimal, validates
         *  Confirms amount "Y/N"
         *  Iterates down and adds currency
         *
         *  Add Another? Y/N
         */
    }

}
