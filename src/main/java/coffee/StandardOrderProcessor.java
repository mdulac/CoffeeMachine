package coffee;

import com.google.common.base.Joiner;

import static coffee.Drink.FRESH_DRINKS;

public class StandardOrderProcessor {

    public static final String ERROR_PREFIX = "M:";

    private static final String NOT_ENOUGH_MONEY = "Not enough money";
    private static final String SEPARATOR = ":";
    private static final String EMPTY = "";

    private static final String EXTRA_HOT_CODE = "h";
    private static final String STICK_CODE = "0";

    public static String process(Order order) {

        if (order.getAmount().compareTo(order.getDrink().getPrice()) < 0) {
            return ERROR_PREFIX + NOT_ENOUGH_MONEY;
        }

        if (FRESH_DRINKS.contains(order.getDrink())) {
            return order.getDrink().getCode() + SEPARATOR + SEPARATOR;
        }

        int sugar = order.getSugar();

        return Joiner.on(SEPARATOR).join(
                order.getDrink().getCode() + (order.isExtraHot() ? EXTRA_HOT_CODE : EMPTY),
                (sugar(sugar)),
                (stick(sugar))
        );
    }

    private static String sugar(int sugar) {
        return sugar > 0 ? EMPTY + sugar : EMPTY;
    }

    private static String stick(int sugar) {
        return sugar > 0 ? STICK_CODE : EMPTY;
    }

}
