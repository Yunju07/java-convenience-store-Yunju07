package store.model;

public class Product {
    private final String name;
    private final Integer price;
    private String promotion;
    private Integer quantity;
    private String getYn;

    public Product(String name, Integer price, Integer quantity, String promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    public Product(String name, Integer price, Integer quantity, String promotion, String getYn) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
        this.getYn = getYn;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public String getPromotion() {
        return promotion;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getGetYn() {
        return getYn;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

    public void increaseQuantity(Integer quantity) {
        this.quantity += quantity;
    }

    public void decreaseQuantity(Integer quantity) {
        this.quantity -= quantity;
    }

    public boolean checkOutOfStock(Integer quantity) {
        return this.quantity >= quantity;
    }
}
