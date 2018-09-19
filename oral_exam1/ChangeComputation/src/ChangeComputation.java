import java.util.Scanner;

/**
 * The ChangeComputation program is a completion of S4_ChangeComputation_Medium.
 * It initializes as a register that starts with random values of currency units,
 * and guides the user through transactions and charges.
 *
 * @author Ben Mitchinson
 * @since Aug 25th 2018
 */
public class ChangeComputation {

    /**
     * Main method that runs through the menu loop, validates choice input, and
     * initializes a new Register object for use.
     *
     * @param args Unused
     * @return Nothing
     * @author Ben Mitchinson
     * @since Aug 25th 2018
     */
    public static void main(String args[]) {
        System.out.println("\nWelcome to Ben's SWD Register" +
                "\n*****************************\n");
        Register mainReg = new Register();
        Scanner input = new Scanner(System.in);

        int choice = 0;
        int centsCharged;
        int centsPaid;
        boolean pos;
        int transactionCents;

        while (choice != 9) {
            mainReg.printTotals();
            printMenu();
            while (!input.hasNextInt()) {
                System.out.println("Please enter an integer option.");
                // Use input.next to get rid of extra newline, essentially a clear.
                input.next();
                printMenu();
            }
            choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1: // Charging a customer
                    System.out.print("How much (in cents) would you like to charge them:");
                    while (!input.hasNextInt()) {
                        System.out.print("Please enter a dollar amount in cents " +
                                "(Ex: $20.58 as '2058'):");
                        input.next();
                    }
                    centsCharged = input.nextInt();
                    input.nextLine();
                    System.out.print("How much (in cents) did they pay?:");
                    while (!input.hasNextInt()) {
                        System.out.print("Please enter a dollar amount in cents " +
                                "(Ex: $20.58 as '2058'):");
                        input.next();
                    }
                    centsPaid = input.nextInt();
                    input.nextLine();
                    mainReg.chargeCustomer(centsCharged, centsPaid);
                    input.nextLine();
                    break;

                case 2: // Adding a manual transaction to the register
                    choice = 0;
                    System.out.println("Would you like to add a...");
                    System.out.println("1.) Positive transaction");
                    System.out.println("2.) Negative transaction");
                    System.out.print("Choice:");
                    while (choice != 1 && choice != 2 && !input.hasNextInt()) {
                        System.out.print("Please enter a 1 or 2:");
                        input.next();
                    }
                    choice = input.nextInt();
                    input.nextLine();
                    pos = (choice == 1) ? (true) : (false);
                    System.out.print("How many cents?:");
                    while (!input.hasNextInt()) {
                        System.out.print("Please enter a dollar amount in cents " +
                                "(Ex: $20.58 as '2058'):");
                        input.next();
                    }
                    transactionCents = input.nextInt();
                    input.nextLine();
                    mainReg.manualTransaction(pos, transactionCents);
                    String action = (pos) ? ("added to") : ("removed from");
                    System.out.println(transactionCents + " cents were " + action + " the register.");
                    System.out.print("Press Enter to dismiss:");
                    input.nextLine();
                    break;

                case 9:
                    break;

                default:
                    System.out.println("Ahhh what? That wasn't an option. Try again.");
            }
            System.out.println();
        }
        System.out.println("Thank you for using Ben's SWD Register");
        System.out.println("~ Register closed ~");
        System.exit(0);
    }

    /**
     * Prints all menu selection options to the user
     */
    // EXPLAIN: Static?
    private static void printMenu() {
        System.out.println("\nOptions:\n*****************************");
        System.out.println("1.) Charge Customer for an Item");
        System.out.println("2.) Manually add a transaction");
        System.out.print("9.) Exit\n\n");
        System.out.print("Choice:");
    }

}