import java.util.ArrayList;
import java.util.List;

import java.util.UUID;

public class ShopService {
    private final ProductRepo productRepo = new ProductRepo();
    private final OrderRepo orderRepo = new OrderMapRepo();

    public Order addOrder(List<String> productIds) {
        List<Product> products = new ArrayList<>();
        for (String productId : productIds) {
            Product productToOrder = productRepo.getProductById(productId).orElseThrow(() -> new IllegalArgumentException("Product mit der Id:" + productId +  "konnte nicht bestellt werden!"));
            products.add(productToOrder);
        }

        Order newOrder = new Order(UUID.randomUUID().toString(), products, Status.COMPLETED);

        return orderRepo.addOrder(newOrder);
    }

    public List<Order> filterByStatus(Status status){

        return new ArrayList<>(orderRepo.getOrders().stream()
                .filter(order -> order.status().equals(status)).toList());
    }

    public Order updateOrder(String orderId, Status newStatus){

        Order updatedOrder =orderRepo.getOrderById(orderId).withStatus(newStatus);
        orderRepo.removeOrder(orderId);
        orderRepo.addOrder(updatedOrder);
        return updatedOrder;
    }
}
