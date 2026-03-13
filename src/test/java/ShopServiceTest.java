import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShopServiceTest {

    @Test
    void addOrderTest() {

        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1");

        //WHEN
        Order actual = shopService.addOrder(productsIds);

        //THEN
        Order expected = new Order("-1", List.of(new Product("1", "Apfel")),Status.PROCESSING, Instant.now());
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

        shopService.addOrder(List.of("1"));
        shopService.addOrder(List.of("1"));

        List<Order> result = shopService.filterByStatus(Status.COMPLETED);

        assertTrue(result.stream().allMatch(order -> order.status() == Status.COMPLETED));
    }

    @Test
    void filterByStatus_shouldReturnEmptyList_whenNoOrdersMatch() {
        ShopService shopService = new ShopService();

        List<Order> result = shopService.filterByStatus(Status.COMPLETED);

        assertTrue(result.isEmpty());
    }
    @Test
    void filterByStatusTestFailure() {
        ShopService shopService = new ShopService();

        shopService.addOrder(List.of("1"));
        shopService.addOrder(List.of("1"));


        List<Order> result = shopService.filterByStatus(Status.IN_DELIVERY);

        assertTrue(result.stream().allMatch(order -> order.status() != Status.IN_DELIVERY));
    }

    @Test
    void updateStatusTest(){

        {
            ShopService shopService = new ShopService();
            Order actual = shopService.addOrder(List.of("1"));

            Order updated = shopService.updateOrder(actual.id(), Status.PROCESSING);

            assertEquals(Status.PROCESSING, updated.status());
        }

    }


}
