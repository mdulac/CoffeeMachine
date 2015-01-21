package coffee;

public class CoffeeMachine {

    private DrinkMaker drinkMaker;

    public CoffeeMachine() {
        drinkMaker = new DrinkMaker();
    }

    public void processOrder(Order order) {

        drinkMaker.sendCommand("T::");
    }
}
