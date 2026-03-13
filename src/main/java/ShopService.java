import java.util.ArrayList;
import java.util.List;

import java.util.UUID;

public class ShopService {
    private final ProductRepo productRepo = new ProductRepo();
    private final OrderRepo orderRepo = new OrderMapRepo();

    public Order addOrder(List<String> productIds) {
        List<Product> products = new ArrayList<>();
        for (String productId : productIds) {
            Product productToOrder = productRepo.getProductById(productId).orElse(null);
            if (productToOrder == null) {
                System.out.println("Product mit der Id: " + productId + " konnte nicht bestellt werden!");
                return null;
            }
            products.add(productToOrder);
        }

        Order newOrder = new Order(UUID.randomUUID().toString(), products, Status.COMPLETED);

        return orderRepo.addOrder(newOrder);
    }

    public List<Order> filterByStatus(Status status){

        return new ArrayList<>(orderRepo.getOrders().stream()
                .filter(order -> order.status().equals(status)).toList());



    }
}
