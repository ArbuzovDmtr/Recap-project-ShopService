import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.*;


@RequiredArgsConstructor

public class ShopService {
    private final ProductRepo productRepo;
    private final OrderRepo orderRepo ;

    public ShopService() {
        productRepo= new ProductRepo();
        orderRepo = new OrderListRepo();
    }

    public Order addOrder(List<String> productIds) {
        List<Product> products = new ArrayList<>();
        for (String productId : productIds) {
            Product productToOrder = productRepo.getProductById(productId).orElseThrow(() -> new IllegalArgumentException("Product mit der Id:" + productId +  " konnte nicht bestellt werden!"));
            products.add(productToOrder);
        }

        Order newOrder = new Order(UUID.randomUUID().toString(), products, Status.COMPLETED, Instant.now());

        return orderRepo.addOrder(newOrder);
    }

    public List<Order> filterByStatus(Status status){

        return orderRepo.getOrders().stream()
                .filter(order -> order.status().equals(status)).toList();
    }

    public Order updateOrder(String orderId, Status newStatus){

        Order updatedOrder = orderRepo.getOrderById(orderId)
                .withStatus(newStatus);

        orderRepo.removeOrder(orderId);
        orderRepo.addOrder(updatedOrder);

        return updatedOrder;
    }


    public void printOrders() {
        orderRepo.getOrders().forEach(order ->
                System.out.println(
                        "Order ID: " + order.id() +
                                ", Status: " + order.status() +
                                ", Time: " + order.timeBenchmark() +
                                ", Products: " + order.products()
                )
        );
    }
}
