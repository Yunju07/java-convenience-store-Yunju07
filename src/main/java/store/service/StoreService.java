package store.service;

import store.dto.ProductDTO;
import store.enums.ErrorMessage;
import store.model.Product;
import store.model.Promotion;
import store.model.Store;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class StoreService {
    private final static String PRODUCTS_FILE_PATH = "src/main/resources/products.md";
    private final static String PROMOTIONS_FILE_PATH = "src/main/resources/promotions.md";

    public Store loadStoreInfo() {
        List<Product> products= loadProducts();
        List<Promotion> promotions = loadPromotions();
        Store store = new Store(products, promotions);

        store = addOutOfStockProduct(store);

        return store;
    }

    public Store addOutOfStockProduct(Store store){
        List<Product> sameProducts;
        Set<String> uniqueProductNames = store.getUniqueProductName();
        for (String productName : uniqueProductNames) {
            sameProducts = store.getProductsByName(productName);
            if (checkOutOfStockProduct(sameProducts)) {
                store.addOutOfStock(sameProducts.get(0));
            }
        }
        return store;
    }

    private List<Product> loadProducts() {
        List<Product> products = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(PRODUCTS_FILE_PATH))) {
            scanner.nextLine();
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                products.add(parseProduct(line));
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return products;
    }

    private List<Promotion> loadPromotions() {
        List<Promotion> promotions = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(PROMOTIONS_FILE_PATH))) {
            scanner.nextLine();
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                promotions.add(parsePromotion(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return promotions;
    }

    public void purchaseProducts(Store store, List<Product> products) {
        for (Product product : products) {
            store.purchase(product);
        }
    }

    public void checkPurchasePossible(Store store, List<ProductDTO> purchases) {
        for (ProductDTO product : purchases) {
            // 제품 확인
            if (!store.checkProduct(product.name)) {
                throw new IllegalArgumentException(ErrorMessage.NONEXISTENT_PRODUCT_ERROR.getMessage());
            }
            // 재고 확인
            if (!store.checkStock(product.name, product.quantity)) {
                throw new IllegalArgumentException(ErrorMessage.OUT_OF_STOCK_ERROR.getMessage());
            }
        }
    }

    private Product parseProduct(String line) {
        String[] productInfo = line.split(",");

        String name = productInfo[0];
        Integer price = Integer.parseInt(productInfo[1]);
        Integer quantity = Integer.parseInt(productInfo[2]);
        String promotion = Optional.ofNullable(productInfo[3])
                                    .filter(value -> !value.equals("null"))
                                    .orElse("");

        return new Product(name, price, quantity, promotion);
    }

    private Promotion parsePromotion(String line) {
        String[] promotionInfo = line.split(",");

        String name = promotionInfo[0];
        Integer buy = Integer.parseInt(promotionInfo[1]);
        Integer get = Integer.parseInt(promotionInfo[2]);
        LocalDate startDate = LocalDate.parse(promotionInfo[3]);
        LocalDate endDate = LocalDate.parse(promotionInfo[4]);

        return new Promotion(name, buy, get, startDate, endDate);
    }

    private boolean checkOutOfStockProduct(List<Product> sameProducts) {
        String promotion = sameProducts.get(0).getPromotion();
        if (sameProducts.size() == 1 && !promotion.equals("")) {
            return true;
        }
        return false;
    }
}
