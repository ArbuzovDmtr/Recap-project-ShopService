void main() {
    Product product1 = new Product("2", "Ananas");
    Product product2 = new Product("3", "Banane");
    Product product3 = new Product("4", "Kiwi");
    Product product4 = new Product("5", "Birne");


    ProductRepo repOfProd = new ProductRepo();
    repOfProd.addProduct(product1);
    repOfProd.addProduct(product2);
    repOfProd.addProduct(product3);
    repOfProd.addProduct(product4);

    OrderRepo orderRepo = new OrderListRepo();

    ShopService service = new ShopService(repOfProd, orderRepo);

    service.addOrder(List.of("2", "3"));
    service.addOrder(List.of("1", "5","2","4"));
    service.addOrder(List.of("3","1"));

    service.printOrders();


}
