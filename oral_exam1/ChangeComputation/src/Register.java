import java.security.SecureRandom;

/**
 * A class dedicated to the inner operations of the cash register, and storing
 * it's values of currency inside a private map.
 *
 */
public class Register {

    // TODO: What's that thing in the S4 Instructions that details the double?
    // TODO: Move this to a string int pair within a map
    //     Value should be the actual dollar amount honestly, not count.
    private int[] currencyArray = new int[8];
    private String[] valueTitleArray = { "Pennies:", "Nickels:", "Dimes:",
            "Quarters:", "Single:", "Fives:", "Tens:", "Twenties:"};

    public Register(){
        SecureRandom randomNumbers = new SecureRandom();
        for (int i=0; i<8; i++){
            currencyArray[i] = randomNumbers.nextInt(16);
        }
    }

    public void printTotals(){
        System.out.println("Current Values:\n" +
                "*****************************");
        // https://docs.oracle.com/javase/7/docs/api/java/util/Formatter.html#syntax
        for (int i=0; i<8; i++){
            System.out.print(
              // %[argument_index$][flags][width][.precision]conversion
              String.format("%-12s %4s %n",valueTitleArray[i],currencyArray[i])
            );
        }
    }

    // TODO: It's fine that this is public right? He was just talking about
    // leaving variables private?
    public void chargeCustomer(){
        /*  TODO: Complete function
         *  Asks for charge cost, stores as String
         *  Parses int and separates by decimal, validates
         *  Confirms amount "Y/N"
         *  Asks for payment made, stores as String
         *  Parses int and separates by decimal, validates
         *  Confirms amount "Y/N"
         *  Calculates change due, iterates through highest to lowest units,
         *      taking into account register totals (non infinite)
         *      What happens if it's not possible?
         *      Somehow stacking what's been removed, as reg needs to output
         *          how many of each unit
         *  Outputs change (or impossible?), how many of each unit, enter to exit loop.
         */
    }

    public void manualTransaction(){
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

    // ?: Constructor iterates through array and randomizes.
        // Is there a way to do that upon assignment instead of iteration?

}
