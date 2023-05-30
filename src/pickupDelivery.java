public class pickupDelivery implements Delivery{
    private String deliveryStatus;


    @Override
    public void collectOrder(FoodBasket foodBasket) {
        deliveryStatus = "Заказ собран";
    }

    @Override
    public void deliver(FoodBasket foodBasket) {
        deliveryStatus = "Заказ готов к выдаче";
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }
}
