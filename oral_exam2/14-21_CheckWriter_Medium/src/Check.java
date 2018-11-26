import java.util.Scanner;

/**
 * Check is an object that represents an amount of USD currency in both integer
 * amounts for cents and dollars, as well as it's equivalent total in written
 * english. It contains methods to prompt the user for their desired integer
 * amount to convert using {@link #promptUser()}, and then is able to convert
 * that amount using {@link #intToEnglish()}.
 * The class holds the necessary english equivalents for converting dollar amounts,
 * and supports the translation of any USD amount under $1000. It also validates
 * user input to make sure it holds an amount within that range.
 *
 * @see Check()
 * @see #promptUser()
 * @see #intToEnglish()
 * @see #validateEntry(String)
 */
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

    /**
     * The promptUser method presents instructions to the user on how to enter
     * their desired currency amount for interpretation, and presents the results
     * of input after conversion using {@link #intToEnglish()}. It also utilizes
     * {@link #validateEntry(String)} to validate what the user has entered as
     * possible for conversion.
     *
     * @see #intToEnglish()
     * @see #validateEntry(String)
     */
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
        intToEnglish();
        System.out.println("That amount in written english is: " + english);
        System.out.println("******************************************");
    }

    /**
     * The intToEnglish method builds an english translation of the already validated
     * and stored cents and ints values. It references english translations stored
     * in private class variables like {@link #tens} and {@link #names}. It handles
     * the case in which the user is only translating cents, dollars, or both in
     * combination.
     *
     * @see #tens
     * @see #names
     * @see #validateEntry(String)
     */
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
                dollarsPart += tens[twoDigit / 10 - 2] + "-" +
                        names[twoDigit % 10 - 1];
            }
        }

        english = dollarsPart + centsPart;
    }

    /**
     * The validateEntry method receives the input from the user and parses it
     * to ints for storage as cent and dollar values. {@link #cent} {@link #dollar}
     *
     * @param entry - String provided by the user for their desired amount to
     *              translate into english.
     * @return true if the entry was successfully validated, false if an error was
     * encountered.
     */
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

}
