public interface Delivery {
    void collectOrder(FoodBasket foodBasket);
    void deliver(FoodBasket foodBasket);
    String getDeliveryStatus();
}