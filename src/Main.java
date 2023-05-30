// Магазин, покупатель, корзина, доставка, метод оплаты - вынесены в отдельные классы (S - принцип единственной ответственности)
// Можно добавлять новую функциональность, не меняя исходные классы (O - принцип открытости/закрытости)
// Интерфейс Delivery отвечает только за доставку (I - принцип сегрегации (разделения) интерфейса)
// Логика доставки зависит от абстракции, интерфейса Delivery (D - принцип инверсии зависимостей)
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
public class Main {
    static final String BLUE = "\u001b[34m";  // Цвета интерфейса вынесены в начало класса (принцип избегания магических чисел)
    static final String GREEN = "\u001b[32m";
    static final String WHITE = "\u001b[37m";
    static final String RESET = "\u001b[0m";

    public static void main(String[] args) {
        printHeader();
        Shop shop = Shop.get();
        FoodBasket foodBasket = null;
        shop.setGoodsList(loadGoods());
        shop.setCustomerList(loadCustomer());
        printGoods(shop.getGoodsList()); // Повторяющиеся действия по выводу в консоль вынесены в отдельные методы (принцип DRY)
        printMenu();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            if ("END".equals(input))
                break;
            int choice = Integer.parseInt(input);
            switch (choice) {
                case (1):
                    System.out.print(RESET + "Введите ключевое слово для фильтра, или ><= для фильтра по ценам (например <100): ");
                    String filterKey = scanner.nextLine();
                    printGoods(filterGoods(filterKey, shop.getGoodsList()));
                    printMenu();
                    break;
                case (2):
                    printGoods(shop.getGoodsList());
                    foodBasket = fillFoodBasket(shop.getGoodsList());
                    printFoodBasket(foodBasket);
                    System.out.println(RESET + "Перейти к оформлению? Yes/No");
                    String yesNo = scanner.nextLine();
                    if ("NO".equals(yesNo.toUpperCase()))
                        break;
                    System.out.println(RESET + "Выберите систему оплаты: 1.Сбербанк; 2.Тинькофф; 3.СБП; 4.ЮMoney (введите номер) ");
                    switch (Integer.parseInt(scanner.nextLine())) {
                        case (1):
                            foodBasket.setPaymentMethod(PaymentMethod.SBER);
                            break;
                        case (2):
                            foodBasket.setPaymentMethod(PaymentMethod.TINKOFF);
                            break;
                        case (3):
                            foodBasket.setPaymentMethod(PaymentMethod.SBP);
                            break;
                        case (4):
                            foodBasket.setPaymentMethod(PaymentMethod.UMONEY);
                            break;
                        default:
                            System.out.println(RESET + "Такая система оплаты не принимается, используйте предложенные");
                            break;
                    }
                    System.out.println(RESET + "Теперь давайте выберем способ получения: 1.Самовывоз; 2.Курьером (введите номер) ");
                    switch (Integer.parseInt(scanner.nextLine())) {
                        case (1):
                            foodBasket.setDeliveryMethod("Самовывоз");
                            break;
                        case (2):
                            foodBasket.setDeliveryMethod("Курьером");
                            break;
                        default:
                            System.out.println("Такого способа получения нет, используйте предложенные");
                            break;
                    }
                    System.out.println(RESET + "Заказ оформлен, доставка завтра. Статус доставки можно посмотреть в разделе доставка");
                    printMenu();
                    break;
                case (3):
                    if (null != foodBasket) {
                        if ("Самовывоз".equals(foodBasket.getDeliveryMethod())) {
                            Delivery delivery = new pickupDelivery();
                            delivery.collectOrder(foodBasket);
                            System.out.println(delivery.getDeliveryStatus());
                        } else {
                            Delivery delivery = new courierDelivery();
                            delivery.collectOrder(foodBasket);
                            System.out.println(delivery.getDeliveryStatus());
                        }
                    } else {
                        System.out.println(RESET + "Вы еще не сделали заказ, поэтому статус доставки недоступен");
                    }
                    printMenu();
                    break;
                default:
                    System.out.println("Такого пункта нет");
                    break;
            }
        }
    }

    public static void printHeader() {
        System.out.println(BLUE + "                                   *** Магазин продукты он-лайн ***\n" +
                "                        *** Всегда свежие продукты с доставкой от 15 минут ***");
    }

    public static void printGoods(List<Goods> list) {
        System.out.println(GREEN + "                   Наименование товара            Стоимость             Производитель");
        System.out.print(WHITE);
        for (int i = 0; i < list.size(); i++) {
            System.out.format("%2d%45s%10s%40s", i + 1, list.get(i).getProductName(), list.get(i).getPrice(), list.get(i).getManufacturerAndCountry());
            System.out.println();
        }
    }

    public static void printMenu() {
        System.out.println();
        System.out.println(BLUE +
                "1.ФИЛЬТР    " +
                "2.КОРЗИНА    " +
                "3.ДОСТАВКА    ");
        System.out.print(RESET + "Выберите пункт меню (1-3) или введите END для выхода: ");
    }

    public static List<Goods> loadGoods() {
        return new ArrayList<>(Arrays.asList(
                new Goods("Молоко 2,5% «Домик в деревне» пастеризованное", "77 ₽", "АО «Вимм-Билль-Данн», Россия"),
                new Goods("Сметана 15% «Домик в деревне»", "53 ₽", "«Манрос М» филиал АО «ВБД», Россия"),
                new Goods("Яйцо куриное С2, 10 шт.", "41 ₽", "РОСКАР, Россия"),
                new Goods("Фарш из говядины «Мясо есть» халяль", "119 ₽", "ООО «Парсит», Россия"),
                new Goods("Сыр Моцарелла для пиццы Unagrande 45%", "459 ₽", "АО «Унагранде Компани», Россия")
        ));
    }

    public static List<Customer> loadCustomer() {
        return new ArrayList<>(Arrays.asList(
                new Customer("ivanov_ivan@mail.ru", "Иван", "+7(985)555-52-62", "Москва, ул.Прямая, д.10, кв.15")));
    }

    public static List<Goods> filterGoods(String input, List<Goods> list) {
        List<Goods> outList = new ArrayList<>();
        if (input.contains("<") || input.contains(">") || input.contains("=")) {
            int intPrice = Integer.parseInt(input.substring(1, input.length()));
            switch (input.substring(0, 1)) {
                case ("<"):
                    for (Goods goods : list) {
                        if (goods.getIntPrice() < intPrice) {
                            outList.add(goods);
                        }
                    }
                    break;
                case (">"):
                    for (Goods goods : list) {
                        if (goods.getIntPrice() > intPrice) {
                            outList.add(goods);
                        }
                    }
                    break;
                case ("="):
                    for (Goods goods : list) {
                        if (goods.getIntPrice() == intPrice) {
                            outList.add(goods);
                        }
                    }
                    break;
                default:
                    System.out.println("Некорректное условие сравнения");
                    break;
            }
        } else {
            for (Goods goods : list) {
                if (goods.getProductName().toLowerCase().contains(input.toLowerCase()) ||
                        goods.getManufacturerAndCountry().toLowerCase().contains(input.toLowerCase())) {
                    outList.add(goods);
                }
            }
        }
        return outList;
    }

    public static FoodBasket fillFoodBasket(List<Goods> list) {
        Scanner scanner = new Scanner(System.in);
        List<Goods> customerGoodsList = new ArrayList<>();
        List<Integer> quantityGoods = new ArrayList<>();
        while (true) {
            System.out.println(WHITE + "Введите номер товара для добавления в корзину (если корзина готова, то введите 0 (ноль)): ");
            int goodsNumber = Integer.parseInt(scanner.nextLine());
            if (goodsNumber == 0)
                break;
            System.out.println(WHITE + "Сколько единиц товара добавить: ");
            int quantity = Integer.parseInt(scanner.nextLine());
            customerGoodsList.add(list.get(goodsNumber - 1));
            quantityGoods.add(quantity);
        }
        return new FoodBasket(customerGoodsList, quantityGoods);
    }

    public static void printFoodBasket(FoodBasket foodBasket) {
        System.out.println("Ваша корзина:");
        for (int i = 0; i < foodBasket.getCustomerGoodsList().size(); i++) {
            System.out.println(foodBasket.getCustomerGoodsList().get(i).getProductName() + " " +
                    foodBasket.getCustomerGoodsList().get(i).getPrice() + " - количество " +
                    foodBasket.getQuantityGoods().get(i) + " шт.");
        }
        System.out.println("Стоимость корзины " + foodBasket.sumFoodBasket() + " руб.");
    }

}