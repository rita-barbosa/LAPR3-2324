package project.ui.console.utils;

import oracle.jdbc.OracleTypes;
import project.domain.dataAccess.DatabaseConnection;
import project.exception.ExcecaoData;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Utils {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

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
                System.out.println("\nERRO: O valor escolhido é invállido."
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
                System.out.println("\nERRO: O valor escolhido é inválido"
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
                    System.out.println("\nERRO: A opção selecionada é inválida.");
                } else {
                    invalid = false;
                }
            } catch (InputMismatchException e) {
                System.out.println("\nERRO: A opção selecionada não é um número.");
                input.nextLine();
            }
        } while (invalid);
        return answer == 1;
    }


//    public static boolean askOptionalData(String optionalData) {
//        System.out.printf("Would you like to provide data about the Property's %s?%n1. Yes%n2. No%n", optionalData);
//        boolean invalid = true;
//        int answer = -1;
//        Scanner input = new Scanner(System.in);
//        do {
//            try {
//                answer = input.nextInt();
//                if (answer != 1 && answer != 2) {
//                    System.out.println("\nERRO: A opção selecionada é inválida.");
//                } else {
//                    invalid = false;
//                }
//            } catch (InputMismatchException e) {
//                System.out.println("\nERRO: A opção selecionada não é um número.");
//                input.nextLine();
//            }
//        } while (invalid);
//        return answer == 1;
//    }

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

    static public int showAndSelectIndex(List<?> list, String header) {
        showList(list, header);
        return selectsIndex(list);
    }

    static public  <K, V> Object showAndSelectIndex(Map<K, V> map, String header) {
        List<Map.Entry<K, V>> listMap = showMap(map, header);
        return selectsIndex(listMap);
    }

    private static <K, V> List<Map.Entry<K, V>> showMap(Map<K, V> map, String header) {
        List<Map.Entry<K, V>> listMap = new ArrayList<>();
        System.out.println(header);
        int index = 0;
        for (Map.Entry<K, V> entry : map.entrySet()) {
            listMap.add(entry);
            index++;
            System.out.println(index + " - " + entry.getKey() + " : " + entry.getValue());
        }
        System.out.println("0 - Cancelar");
        return listMap;
    }

    static public void showList(List<?> list, String header) {
        System.out.println(header);
        int index = 0;
        for (Object o : list) {
            index++;

            System.out.println(index + " - " + o.toString());
        }
        System.out.println("0 - Cancelar");
    }

    static public int selectsIndex(List<?> list) {
        String input;
        int value;
        do {
            input = Utils.readLineFromConsole("Selecione a opção desejada: ");
            try {
                value = Integer.parseInt(input);
            } catch (NumberFormatException ex) {
                value = -1;
            }
        } while (value < 0 || value > list.size());
        return value - 1;
    }

    public static List<String> resultSetTypeToList(ResultSet resultSet, String paramName) throws SQLException {
        List<String> entryList = new ArrayList<>();
        while (true) {
            if (!resultSet.next()) break;
            String entry = resultSet.getString(paramName);
            entryList.add(entry);
        }
        return entryList;
    }

    public static void validateDate(String date){
        try{
            ExcecaoData.verificarData(date);
        }catch (ExcecaoData e) {
            System.out.println(e.getMessage());
        }
    }

    public static <K, V> void resultSetMapToList(Map<K, V> map, ResultSet resultSet) throws SQLException {
        while (true) {
            if (!resultSet.next()) break;
            K key = (K) resultSet.getObject(1);
            V values = (V) resultSet.getObject(2);
            map.put(key, values);
        }
    }


    public static int getNewIdOperation() throws SQLException {
        CallableStatement callStmt = null;
        int newIdOperacao;
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            callStmt = connection.prepareCall("{ ? = call novoIdOperacao() }");

            callStmt.registerOutParameter(1, OracleTypes.INTEGER);

            callStmt.execute();

            newIdOperacao = callStmt.getInt(1);

        } finally {
            if (!Objects.isNull(callStmt)) {
                callStmt.close();
            }
        }
        return newIdOperacao;
    }
}
