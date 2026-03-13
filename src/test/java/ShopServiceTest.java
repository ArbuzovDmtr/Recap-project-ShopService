import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShopServiceTest {

    @Test
    void addOrderTest() {
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1");

        //WHEN
        Order actual = shopService.addOrder(productsIds);

        //THEN
        Order expected = new Order("-1", List.of(new Product("1", "Apfel")),Status.PROCESSING);
        assertEquals(expected.products(), actual.products());
        assertNotNull(expected.id());
    }

    @Test
    void addOrderTest_whenInvalidProductId_expectNull() {
        assertThrows(IllegalArgumentException.class,()->{
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1", "2");
        shopService.addOrder(productsIds);

    });
    }
    @Test
    void filterByStatusTest() {

        ShopService shopService = new ShopService();
        Order actual = shopService.addOrder(List.of("1"));
        Order actual2 = shopService.addOrder(List.of("1"));


        Order order = new Order(actual.id(), List.of(new Product("1", "Apfel")),Status.COMPLETED);
        Order order2 = new Order(actual2.id(), List.of(new Product("1", "Apfel")),Status.COMPLETED);


        List<Order> expected = List.of(order2,order);
        assertEquals(new HashSet<>(expected),new HashSet<>(shopService.filterByStatus(Status.COMPLETED)));
    }

    @Test
    void filterByStatusTestFailure() {
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1");

        //WHEN
        Order actual = shopService.addOrder(productsIds);
        Order actual2 = shopService.addOrder(List.of("1"));


        Order order = new Order(actual.id(), List.of(new Product("1", "Apfel")),Status.COMPLETED);
        Order order2 = new Order(actual2.id(), List.of(new Product("1", "Apfel")),Status.PROCESSING);


        List<Order> expected = List.of(order2,order);
        assertNotEquals(new HashSet<>(expected),new HashSet<>(shopService.filterByStatus(Status.COMPLETED)));

    }
}
