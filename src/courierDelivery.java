public class courierDelivery implements Delivery {
    private String deliveryStatus;


    @Override
    public void collectOrder(FoodBasket foodBasket) {
        deliveryStatus = "Заказ собран";
    }

    @Override
    public void deliver(FoodBasket foodBasket) {
        deliveryStatus = "Заказ передан курьеру";
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }
}
