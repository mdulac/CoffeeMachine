package coffee;

import java.math.BigDecimal;
import java.util.EnumMap;

import static coffee.StandardOrderProcessor.ERROR_PREFIX;
import static coffee.StandardOrderProcessor.process;
import static java.math.BigDecimal.ZERO;
import static java.math.BigDecimal.valueOf;

public class CoffeeMachine {

    private final DrinkMaker drinkMaker;

    private final EnumMap<Drink, Integer> drinksOrdered;

    public CoffeeMachine(DrinkMaker drinkMaker) {
        this.drinkMaker = drinkMaker;
        this.drinksOrdered = new EnumMap<>(Drink.class);
    }

    public void processOrder(Order order) {
        String command = process(order);
        drinkMaker.sendCommand(command);

        if (!command.startsWith(ERROR_PREFIX)) {
            drinksOrdered.merge(order.getDrink(), 1, Integer::sum);
        }
    }

    public BigDecimal account() {
        return drinksOrdered
                .entrySet()
                .parallelStream()
                .map(e -> e.getKey().getPrice().multiply(valueOf(e.getValue())))
                .reduce(ZERO, BigDecimal::add);
    }

}
