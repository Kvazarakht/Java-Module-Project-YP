import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int persons = check();

        Calculate oneCalc = new Calculate();
        oneCalc.addProduct();
        oneCalc.showSoppingList();
        oneCalc.showCalculation(persons);
    }

    private static int check() {
        String regex = "[0-9]+";
        int num;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("На скольких человек необходимо разделить счёт ?");
            String count = scanner.nextLine();
            if (count.matches(regex)) {
                num = Integer.parseInt(count);
                if (num > 1) break;
            }
            System.out.println("Введено некорректное значение для подсчёта.");
        }
        return num;
    }
}

class Calculate {
    HashMap<String, Double> products = new HashMap<>();

    void addProduct() {
        String regex = "[0-9]+\\.[0-9]{2}";
        String regexZero = "0+\\.0+";
        Scanner scanner = new Scanner(System.in);
        boolean choice = true;
        while (choice) {
            System.out.println("Введите название товара :");
            String key = scanner.nextLine();
            if (key.equalsIgnoreCase("Завершить") && products.isEmpty()) {
                break;
            }
            if (key.equalsIgnoreCase("Завершить") && !products.isEmpty()) break;
            double volume = 0.0;
            String price;
            while (true) {
                System.out.println("Введите стоимость товара :");
                price = scanner.nextLine();

                if (products.isEmpty() && price.equalsIgnoreCase("Завершить")) {

                    choice = false;
                    break;
                }
                if (price.equalsIgnoreCase("Завершить")) {
                    System.out.println("!!!!!!!");
                    choice = false;
                    break;
                }

                if (price.matches(regex)) {
                    volume = Double.parseDouble(price);
                    break;
                } else if (price.matches(regex) && price.matches(regexZero)) {
                    System.out.println("Цена не может равняться нулю");
                    break;
                } else System.out.println("Указана неверная цена");
                if (!choice) break;

            }
            if (!key.equals("") && !price.equals("") && volume != 0.0) products.put(key, volume);
            if (!price.equalsIgnoreCase("завершить")) {
                System.out.println("Товар добавлен в расчет");
                System.out.println("Хотите добавить ещё один товар ?");
            }
            if (price.equalsIgnoreCase("завершить") || scanner.nextLine().equalsIgnoreCase("завершить"))
                break;
        }
    }

    void showSoppingList() {
        int count = 1;
        System.out.println("Добавленные товары:");
        for (Map.Entry<String, Double> product : products.entrySet()) {
            System.out.println(count + ") товар: " + product.getKey() + "; цена: " + product.getValue());
            count++;
        }
        if (products.size() == 0) System.out.println("отсутствуют");
    }

    void showCalculation(int persons) {
        double sum = 0;
        for (Map.Entry<String, Double> product : products.entrySet()) {
            sum += product.getValue();
        }
        String rubl = switch ((int) sum / persons) {
            case 1 -> "рубль";
            case 2, 3, 4 -> "рубля";
            default -> "рублей";
        };
        double moneyOnePerson = sum / persons;
        System.out.println("Каждый человек должен заплатить: " + String.format("%.2f", moneyOnePerson) + " " + rubl);
    }
}