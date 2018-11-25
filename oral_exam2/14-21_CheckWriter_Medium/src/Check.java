import java.util.Scanner;

public class Check {
    // Conversion Variables
    private int dollar = -1;
    private int cent = -1;
    private String english;
    // Input
    private Scanner input = new Scanner(System.in);
    // English associations
    private String[] names = {"ONE", "TWO", "THREE", "FOUR",
            "FIVE", "SIX", "SEVEN", "EIGHT", "NINE", "TEN", "ELEVEN", "TWELVE",
            "THIRTEEN", "FOURTEEN", "FIFTEEN", "SIXTEEN", "SEVENTEEN", "EIGHTEEN",
            "NINETEEN"};
    private String[] tens = {"TWENTY", "THIRTY", "FOURTY", "FIFTY", "SIXTY",
            "SEVENTY", "EIGHTY", "NINETY"};

    public void promptUser() {
        String entry;
        boolean entryIsValid = false;

        System.out.println("\nEnter in a check amount (Ex: 103.43 | .43 | 104)");
        while (!entryIsValid) {
            System.out.println("Entries must be less than $1000, and may be comprised" +
                    " of only cents, only dollars, or a combination of both");
            System.out.print("\nEntry:");
            entry = input.nextLine();
            if (entry.equals("-1")) {
                System.out.println("Thank you for testing the demo");
                System.exit(0);
            }
            entryIsValid = validateEntry(entry);
        }
        // line to confirm the validated user input
        // inputCheck();
        intToEnglish();
        System.out.println("That amount in written english is: " + english);
        System.out.println("******************************************");
    }

    private void intToEnglish() {
        String centsPart = "";
        String dollarsPart = "";

        if (this.cent != -1) {
            centsPart += cent + "/100";
        }

        if (this.dollar != -1) {
            if (cent != -1) {
                centsPart = " and " + centsPart;
            }
            if (dollar > 99) {
                dollarsPart += (names[dollar / 100 - 1] + " hundred ");
            }

            int twoDigit = dollar % 100;
            if (twoDigit < 20) {
                dollarsPart += names[twoDigit - 1];
            } else {
                dollarsPart += tens[twoDigit/10-2] + "-" +
                        names[twoDigit%10-1];
            }
        }

        english = dollarsPart + centsPart;
    }

    private boolean validateEntry(String entry) {
        boolean valid = true;
        int amount;

        String[] split = entry.split("\\.");

        if (split.length != 1 && split.length != 2) {
            System.out.println("Your entry should have at most 1 '.'");
            return false;
        }

        if (entry.charAt(entry.length() - 1) == '.') {
            System.out.println("Your entry should never end in a '.'\n" +
                    "If you're entering only a dollar amount, leave off the '.'");
            return false;
        }

        int i = 0;
        for (String part : split) {
            if (!(i == 0 && split.length == 2 && part.equals(""))) {
                try {
                    amount = Integer.valueOf(part);
                } catch (NumberFormatException e) {
                    System.out.println("Part of your input (aside from any '.') " +
                            "was not an integer.");
                    return false;
                }
                if (!entry.contains("." + String.valueOf(amount))) {
                    if (amount < 1000 && amount > 0) {
                        this.dollar = amount;
                    } else {
                        System.out.println("Your dollar amount was out of range.");
                        return false;
                    }
                } else {
                    if (0 < amount && amount < 100) {
                        this.cent = amount;
                    } else {
                        System.out.println("Your cent amount was out of range.");
                        return false;
                    }
                }
            }
            i++;
        }

        return valid;
    }

    private void inputCheck() {
        System.out.print("Amount entered: ");
        System.out.println("Dollars:" + dollar + " Cents:" + cent);
    }

}
