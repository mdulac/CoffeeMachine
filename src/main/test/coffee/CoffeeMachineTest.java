package coffee;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

import static coffee.Drink.*;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CoffeeMachineTest {

    @Mock
    public DrinkMaker drinkMaker;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private CoffeeMachine coffeeMachine;

    @Before
    public void setUp() throws Exception {
        coffeeMachine = new CoffeeMachine(drinkMaker);
    }

    @Test
    public void should_throw_exception_if_drink_is_null() {
        // Given
        expectedException.expect(IllegalArgumentException.class);

        // When
        coffeeMachine.processOrder(new Order(null, 0, ONE));
    }

    @Test
    public void should_throw_exception_if_drink_sugar_is_negative() {
        // Given
        expectedException.expect(IllegalArgumentException.class);

        // When
        coffeeMachine.processOrder(new Order(TEA, -1, ONE));
    }

    @Test
    public void should_throw_exception_if_drink_sugar_is_greater_than_two() {
        // Given
        expectedException.expect(IllegalArgumentException.class);

        // When
        coffeeMachine.processOrder(new Order(TEA, 3, ONE));
    }

    @Test
    public void should_create_tea_with_no_sugar() {
        // Given
        Order order = new Order(TEA, 0, ONE);

        // When
        coffeeMachine.processOrder(order);

        // Then
        verify(drinkMaker, times(1)).sendCommand("T::");
    }

    @Test
    public void should_create_tea_with_one_sugar() {
        // Given
        Order order = new Order(TEA, 1, ONE);

        // When
        coffeeMachine.processOrder(order);

        // Then
        verify(drinkMaker, times(1)).sendCommand("T:1:0");
    }

    @Test
    public void should_create_chocolate_with_two_sugar() {
        // Given
        Order order = new Order(CHOCOLATE, 2, ONE);

        // When
        coffeeMachine.processOrder(order);

        // Then
        verify(drinkMaker, times(1)).sendCommand("H:2:0");
    }

    @Test
    public void should_create_coffee_with_no_sugar() {
        // Given
        Order order = new Order(COFFEE, 0, ONE);

        // When
        coffeeMachine.processOrder(order);

        // Then
        verify(drinkMaker, times(1)).sendCommand("C::");
    }


    @Test
    public void should_create_orange_with() {
        // Given
        Order order = new Order(ORANGE, 0, ONE);

        // When
        coffeeMachine.processOrder(order);

        // Then
        verify(drinkMaker, times(1)).sendCommand("O::");
    }

    @Test
    public void should_create_message_if_not_enough_money() throws Exception {
        CoffeeMachine machine = new CoffeeMachine(drinkMaker);

        // When
        machine.processOrder(new Order(TEA, 0, ZERO));

        // Then
        verify(drinkMaker, times(1)).sendCommand("M:Not enough money");
    }

    @Test
    public void should_create_command_if_enough_money() throws Exception {
        CoffeeMachine machine = new CoffeeMachine(drinkMaker);

        // When
        machine.processOrder(new Order(TEA, 0, ONE));

        // Then
        verify(drinkMaker, times(1)).sendCommand("T::");
    }

    @Test
    public void should_create_tea_with_extra_hot() {
        // Given
        Order order = new Order(TEA, 0, ONE, true);

        // When
        coffeeMachine.processOrder(order);

        // Then
        verify(drinkMaker, times(1)).sendCommand("Th::");
    }

    @Test
    public void should_not_create_orange_with_extra_hot() {
        // Given
        Order order = new Order(ORANGE, 0, ONE, true);

        // When
        coffeeMachine.processOrder(order);

        // Then
        verify(drinkMaker, times(1)).sendCommand("O::");
    }

    @Test
    public void should_return_order_count_with_correct_orders() {
        // Given
        coffeeMachine.processOrder(new Order(COFFEE, 0, ONE, true));
        coffeeMachine.processOrder(new Order(TEA, 0, ONE, true));
        coffeeMachine.processOrder(new Order(ORANGE, 0, ONE, true));
        coffeeMachine.processOrder(new Order(TEA, 0, ONE, true));
        coffeeMachine.processOrder(new Order(CHOCOLATE, 0, ONE, true));

        // When
        BigDecimal count = coffeeMachine.account();

        // Then
        assertThat(count).isEqualTo("2.5");
    }

    @Test
    public void should_return_order_count_with_incorrect_orders() {
        // Given
        coffeeMachine.processOrder(new Order(COFFEE, 0, ZERO, true));
        coffeeMachine.processOrder(new Order(TEA, 0, ZERO, true));
        coffeeMachine.processOrder(new Order(ORANGE, 0, ZERO, true));
        coffeeMachine.processOrder(new Order(TEA, 0, ZERO, true));
        coffeeMachine.processOrder(new Order(CHOCOLATE, 0, ZERO, true));

        // When
        BigDecimal count = coffeeMachine.account();

        // Then
        assertThat(count).isEqualTo("0");
    }

    @Test
    public void should_return_order_count_with_correct_and_incorrect_orders() {
        // Given
        coffeeMachine.processOrder(new Order(COFFEE, 0, ONE, true));
        coffeeMachine.processOrder(new Order(TEA, 0, ZERO, true));
        coffeeMachine.processOrder(new Order(ORANGE, 0, ONE, true));
        coffeeMachine.processOrder(new Order(TEA, 0, ZERO, true));
        coffeeMachine.processOrder(new Order(CHOCOLATE, 0, ONE, true));

        // When
        BigDecimal count = coffeeMachine.account();

        // Then
        assertThat(count).isEqualTo("1.7");
    }

}