import java.security.SecureRandom;

public class Register {

    // Create array of ints for each value type
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
              // TODO: add subtotals of amounts? Multiplyer and total in reg?
            );
        }
    }

    // TODO: Calculate total change in register

    // ?: Constructor iterates through array and randomizes.
        // Is there a way to do that upon assignment instead of iteration?

}
