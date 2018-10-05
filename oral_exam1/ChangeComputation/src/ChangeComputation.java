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
     * Runs through the menu loop, validates choice input, and
     * initializes a new Register object for use.
     *
     * @param args Unused
     */
    public static void main(String args[]) {
        System.out.println("\nWelcome to Ben's SWD Register" +
                "\n*****************************\n");
        Register mainReg = new Register();
        Scanner input = new Scanner(System.in);

        System.out.println("Code:"+mainReg.toString());

        int choice = 0;
        int centsCharged;
        int centsPaid;
        int[] unitsPaid = new int[8];
        int[] requestedCents;
        boolean pos;
        int transactionCents;

        while (choice != 9) {
            mainReg.printTotals();
            printMenu();
            while (!input.hasNextInt()) {
                input.nextLine();
                System.out.println("Please enter an integer option.");
                printMenu();
            }
            choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1: // Charging a customer
                    centsCharged = -1;
                    while (centsCharged <= 0) {
                        System.out.print("How many cents would you like to charge them:");
                        if (!input.hasNextInt()) {
                            input.nextLine();
                            System.out.println("Please enter an integer value.");
                        } else {
                            centsCharged = input.nextInt();
                            input.nextLine();
                            if (centsCharged <= 0) {
                                System.out.println("Please enter a positive dollar amount in cents " +
                                        "(Ex: $20.58 as '2058'):");
                            }
                        }
                    }
                    mainReg.chargeCustomer(centsCharged);
                    break;

                case 2: // Adding a manual transaction to the register
                    choice = 0;
                    System.out.println("Would you like to add a...");
                    System.out.println("1.) Positive transaction");
                    System.out.println("2.) Negative transaction");
                    while (choice != 1 && choice != 2) {
                        System.out.print("Choice:");
                        if (!input.hasNextInt()) {
                            input.nextLine();
                            System.out.println("Please enter 1 or 2");
                        } else {
                            choice = input.nextInt();
                            input.nextLine();
                            if (choice != 1 && choice != 2) {
                                System.out.println("Please enter 1 or 2");
                            }
                        }
                    }
                    pos = (choice == 1);
                    requestedCents = mainReg.askForCents();
                    mainReg.manualTransaction(pos, requestedCents);
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
    private static void printMenu() {
        System.out.println("\nOptions:\n*****************************");
        System.out.println("1.) Charge customer for an item");
        System.out.println("2.) Manually add a transaction");
        System.out.print("9.) Exit\n\n");
        System.out.print("Choice:");
    }

}