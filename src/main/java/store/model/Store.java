package store.model;

import store.enums.Membership;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Store {
    private List<Product> stock;
    private List<Promotion> promotions;
    private final Membership membership = Membership.STANDARD;

    public Store(List<Product> stock, List<Promotion> promotions) {
        this.stock = stock;
        this.promotions = promotions;
    }

    public List<Product> getStock() {
        return stock;
    }

    public Membership getMembership() {
        return membership;
    }

    public Set<String> getUniqueProductName() {
        return stock.stream()
                .map(Product::getName)
                .collect(Collectors.toSet());
    }

    public List<Product> getProductsByName(String productName) {
        return stock.stream()
                .filter(product -> product.getName().equals(productName))
                .collect(Collectors.toList());
    }

    public Promotion getPromotionByName(String promotionName) {
        for (Promotion promotion : promotions) {
            if (promotion.getName().equals(promotionName)) {
                return promotion;
            }
        }
        return null;
    }

    public void purchase(Product product) {
        for (Product stockProduct : stock) {
            if (stockProduct.getName().equals(product.getName()) &&
                    stockProduct.getPromotion().equals(product.getPromotion())){
                stockProduct.decreaseQuantity(product.getQuantity());
            }
        }
    }

    public void addOutOfStock(Product product) {
        int i;
        for (i = 0; i < stock.size(); i++){
            if (stock.get(i).getName().equals(product.getName())) {
                break;
            }
        }
        Product outOfStockProduct = new Product(product.getName(), product.getPrice(), 0, "");
        stock.add(i + 1, outOfStockProduct);
    }

    public boolean checkProduct(String productName) {
        for (Product product : stock) {
            if (product.getName().equals(productName)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkStock(String productName, Integer productQuantity) {
        List<Product> products = this.getProductsByName(productName);
        Integer stockQuantity = products.stream()
                .filter(product -> product.getName().equals(productName))
                .mapToInt(Product::getQuantity)
                .sum();

        if (stockQuantity < productQuantity) {
            return false;
        }
        return true;
    }
}
