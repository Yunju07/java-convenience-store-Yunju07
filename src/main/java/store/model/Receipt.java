package store.model;

import java.util.List;

public class Receipt {
    private final List<Product> totalProducts;
    private final List<Product> giftProducts;
    private final Payment payment;

    public Receipt(List<Product> totalProducts,
                   List<Product> giftProducts,
                   Payment payment) {
        this.totalProducts = totalProducts;
        this.giftProducts = giftProducts;
        this.payment = payment;
    }

    public List<Product> getTotalProducts() {
        return totalProducts;
    }

    public List<Product> getGiftProducts() {
        return giftProducts;
    }

    public Payment getPayment() {
        return payment;
    }
}
