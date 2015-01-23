package coffee;

import java.math.BigDecimal;
import java.util.Objects;

public class Order {

    private static final String ORDER_MALFORMED_MESSAGE = "Order is malformed";

    private final Drink drink;

    private final int sugar;

    private final BigDecimal amount;

    private final boolean extraHot;

    public Order(Drink drink, int sugar, BigDecimal amount) {
        this(drink, sugar, amount, false);
    }

    public Order(Drink drink, int sugar, BigDecimal amount, boolean extraHot) {

        if (Objects.isNull(drink) || sugar < 0 || sugar > 2) {
            throw new IllegalArgumentException(ORDER_MALFORMED_MESSAGE);
        }

        this.amount = amount;
        this.drink = drink;
        this.sugar = sugar;
        this.extraHot = extraHot;
    }

    public Drink getDrink() {
        return drink;
    }

    public int getSugar() {
        return sugar;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public boolean isExtraHot() {
        return extraHot;
    }
}
