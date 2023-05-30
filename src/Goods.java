public class Goods {
    private String productName;
    private String price;
    private String manufacturerAndCountry;
    private int rating;

    public Goods(String productName, String price, String manufacturerAndCountry) {
        this.productName = productName;
        this.price = price;
        this.manufacturerAndCountry = manufacturerAndCountry;
        this.rating = 0;
    }

    public String getProductName() {
        return productName;
    }

    public String getPrice() {
        return price;
    }

    public int getIntPrice() {
        return Integer.parseInt(price.substring(0, price.indexOf(" ")));
    }

    public String getManufacturerAndCountry() {
        return manufacturerAndCountry;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
