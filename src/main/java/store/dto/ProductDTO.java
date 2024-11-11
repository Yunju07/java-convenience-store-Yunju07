package store.dto;

public class ProductDTO {
    public String name;
    public Integer price;
    public Integer quantity;
    public String promotion;

    public ProductDTO(String name,
                      Integer quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public ProductDTO(String name,
                      Integer price,
                      Integer quantity,
                      String promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }
}
