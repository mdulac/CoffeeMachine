package coffee;

import java.math.BigDecimal;
import java.util.Set;

import static java.util.EnumSet.of;

public enum Drink {

    COFFEE("C", BigDecimal.valueOf(0.6)),
    CHOCOLATE("H", BigDecimal.valueOf(0.5)),
    TEA("T", BigDecimal.valueOf(0.4)),
    ORANGE("O", BigDecimal.valueOf(0.6));

    private final String code;

    private final BigDecimal price;

    public static final Set<Drink> HOT_DRINKS = of(COFFEE, CHOCOLATE, TEA);
    public static final Set<Drink> FRESH_DRINKS = of(ORANGE);

    Drink(String code, BigDecimal price) {
        this.code = code;
        this.price = price;
    }

    public String getCode() {
        return code;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
