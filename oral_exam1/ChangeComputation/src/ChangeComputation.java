import java.util.Scanner;

public class ChangeComputation {

    // TODO: JDoc Comments
    public static void main(String args[]){
        System.out.println("\nWelcome to Ben's SWD Register:" +
                "\n*****************************\n");
        Register mainReg = new Register();

        Scanner input = new Scanner(System.in);

        int choice = 0;
        while (choice != 9){
            mainReg.printTotals();
            printMenu();
            // TODO: validate nextInt? Try catch?
            choice = input.nextInt();
            switch (choice){
                case 1:
                    System.out.println("Charging customer for an item.");
                    mainReg.chargeCustomer();
                    break;
                case 9:
                    System.out.println("Closing Register");
                    break;
                default:
                    System.out.println("Ahhh what? invalid input. Try again.");
            }

            // Choice switch statement for register operations.
            System.out.println();
        }
        System.exit(0);
    }

    private static void printMenu(){
        System.out.println("\nOptions:\n*****************************");
        System.out.println("1.) Charge Customer for an Item");
        System.out.println("2.) Manually add a transaction");
        System.out.print("9.) Exit\n\n");
        System.out.print("Choice:");
    }

}