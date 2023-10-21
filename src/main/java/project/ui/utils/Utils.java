package project.ui.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Utils {

    private static Boolean isApplicationLaunched = false;


    static public Boolean getApplicationLaunched() {
        return isApplicationLaunched;
    }

    static public String readLineFromConsole(String prompt) {
        try {
            System.out.println("\n" + prompt);

            InputStreamReader converter = new InputStreamReader(System.in);
            BufferedReader in = new BufferedReader(converter);

            return in.readLine();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    static public int readIntegerFromConsole(String prompt) {
        boolean invalid = true;
        Integer value = null;
        do {
            try {
                value = Integer.parseInt(readLineFromConsole(prompt));
                invalid = false;
            } catch (NumberFormatException e) {
                // Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, e);
                System.out.println("\nERROR: Value typed is invalid."
                        + " (" + e.getClass().getSimpleName() + ")");
            }
        } while (invalid);
        return value;
    }

    static public double readDoubleFromConsole(String prompt) {
        boolean invalid = true;
        Double value = null;
        do {
            try {
                value = Double.parseDouble(readLineFromConsole(prompt));
                invalid = false;
            } catch (NumberFormatException e) {
                // Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, e);
                System.out.println("\nERROR: Value typed is invalid"
                        + " (" + e.getClass().getSimpleName() + " - Decimal separator is a dot)");
            }
        } while (invalid);
        return value;
    }

    public static Boolean askDirectQuestion(String message) {
        System.out.printf("\n%s \n1. Yes \n2. No\n", message);
        boolean invalid = true;
        int answer = -1;
        Scanner input = new Scanner(System.in);
        do {
            try {
                answer = input.nextInt();
                if (answer != 1 && answer != 2) {
                    System.out.println("\nERROR: Option selected is invalid.");
                } else {
                    invalid = false;
                }
            } catch (InputMismatchException e) {
                System.out.println("\nERROR: Option selected is not a number.");
                input.nextLine();
            }
        } while (invalid);
        return answer == 1;
    }


    public static boolean askOptionalData(String optionalData) {
        System.out.printf("Would you like to provide data about the Property's %s?%n1. Yes%n2. No%n", optionalData);
        boolean invalid = true;
        int answer = -1;
        Scanner input = new Scanner(System.in);
        do {
            try {
                answer = input.nextInt();
                if (answer != 1 && answer != 2) {
                    System.out.println("\nERROR: Option selected is invalid.");
                } else {
                    invalid = false;
                }
            } catch (InputMismatchException e) {
                System.out.println("\nERROR: Option selected is not a number.");
                input.nextLine();
            }
        } while (invalid);
        return answer == 1;
    }

    static public Date readDateFromConsole(String prompt) {
        do {
            try {
                String strDate = readLineFromConsole(prompt);

                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");

                Date date = df.parse(strDate);

                return date;
            } catch (ParseException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            }
        } while (true);
    }

    static public boolean confirm(String message) {
        String input;
        do {
            input = Utils.readLineFromConsole("\n" + message + "\n");
        } while (!input.equalsIgnoreCase("s") && !input.equalsIgnoreCase("n"));

        return input.equalsIgnoreCase("s");
    }

    static public Object showAndSelectOne(List<?> list, String header) {
        showList(list, header);
        return selectsObject(list);
    }

    static public int showAndSelectIndex(List<?> list, String header) {
        showList(list, header);
        return selectsIndex(list);
    }

    static public void showList(List<?> list, String header) {
        System.out.println(header);

        int index = 0;
        for (Object o : list) {
            index++;

            System.out.println(index + ". " + o.toString());
        }
        System.out.println();
        System.out.println("0 - Cancel");
    }

    static public Object selectsObject(List<?> list) {
        String input;
        int value;
        do {
            input = Utils.readLineFromConsole("Type your option: ");
            value = Integer.parseInt(input);
        } while (value < 0 || value > list.size());

        if (value == 0) {
            return null;
        } else {
            return list.get(value - 1);
        }
    }

    /**
     * This method display and aks to select a type of sorting.
     *
     * @return the type of sorting chosen
     */
    public static String sortSelection(String prompt) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Sort types available:");
        System.out.println("1. Ascending");
        System.out.println("2. Descending");
        int option = 0;
        boolean invalid = true;
        do {
            try {
                while (option < 1 || option > 2) {
                    option = sc.nextInt();
                    if (option == 1) {
                        return "Ascending";
                    } else if (option == 2) {
                        return "Descending";
                    }
                }
                invalid = false;
            } catch (InputMismatchException e) {
                System.out.println("\nERROR: Option selected is invalid."
                        + " (" + e.getClass().getSimpleName() + ")");
                sc.nextLine();
            }
        } while (invalid);
        return null;
    }

    static public int selectsIndex(List<?> list) {
        String input;
        int value;
        do {
            input = Utils.readLineFromConsole("Type your option: ");
            try {
                value = Integer.parseInt(input);
            } catch (NumberFormatException ex) {
                value = -1;
            }
        } while (value < 0 || value > list.size());
        return value - 1;
    }

    public static void setAplicationLaunched(boolean b) {
        isApplicationLaunched = b;
    }
}
