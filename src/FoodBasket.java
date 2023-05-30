import java.util.List;

public class FoodBasket {
    private Customer customer;
    private List<Goods> customerGoodsList;
    private List<Integer> quantityGoods;
    private PaymentMethod paymentMethod;
    private String deliveryMethod;

    public FoodBasket(List<Goods> customerGoodsList, List<Integer> quantityGoods) {
        this.customerGoodsList = customerGoodsList;
        this.quantityGoods = quantityGoods;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Goods> getCustomerGoodsList() {
        return customerGoodsList;
    }

    public void setCustomerGoodsList(List<Goods> customerGoodsList) {
        this.customerGoodsList = customerGoodsList;
    }

    public List<Integer> getQuantityGoods() {
        return quantityGoods;
    }

    public void setQuantityGoods(List<Integer> quantityGoods) {
        this.quantityGoods = quantityGoods;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public int sumFoodBasket() {
        int sum = 0;
        for (int i = 0; i < customerGoodsList.size(); i++) {
            sum += (customerGoodsList.get(i).getIntPrice() * quantityGoods.get(i));
        }
        return sum;
    }
}
