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
     *   initializes a new Register object for use.
     * @author Ben Mitchinson
     * @since Aug 25th 2018
     * @param args Unused
     * @return Nothing
     */
    public static void main(String args[]){
        System.out.println("\nWelcome to Ben's SWD Register" +
                "\n*****************************\n");
        Register mainReg = new Register();

        Scanner input = new Scanner(System.in);
        int choice = 0;

        while (choice != 9){
            mainReg.printTotals();
            printMenu();
            while (!input.hasNextInt()) {
                System.out.println("Please enter an integer choice.");
                // Use input.next to get rid of given input, essentially a clear.
                input.next();
                printMenu();
            }
            choice = input.nextInt();
            switch (choice){
                case 1:
                    System.out.println("Charging customer for an item:");
                    mainReg.chargeCustomer();
                    break;
                case 2:
                    System.out.println("Manually adding a transaction:");
                    mainReg.chargeCustomer();
                    break;
                case 9:
                    break;
                default:
                    System.out.println("Ahhh what? That wasn't a choice. Try again.");
            }
            System.out.println();
        }
        System.out.println("Thank you for using Ben's SWD Register");
        System.out.println("~ Register closed ~");
        System.exit(0);
    }

    /**
     * Prints all menu selection options to the user.
     * @return Nothing
     */
    private static void printMenu(){
        System.out.println("\nOptions:\n*****************************");
        System.out.println("1.) Charge Customer for an Item");
        System.out.println("2.) Manually add a transaction");
        System.out.print("9.) Exit\n\n");
        System.out.print("Choice:");
    }

}