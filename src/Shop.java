import java.util.List;

public class Shop {
    private static Shop instance = null;
    private List<Customer> customerList;
    private List<Goods> goodsList;

    private Shop() {}

    public static Shop get() {
        if (instance == null)
            instance = new Shop();
        return instance;
    }

    public List<Customer> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<Customer> customerList) {
        this.customerList = customerList;
    }

    public List<Goods> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<Goods> goodsList) {
        this.goodsList = goodsList;
    }
}
