package store.service;

import store.converter.StoreConverter;
import store.enums.GuideMessage;
import store.model.Product;
import store.model.Promotion;
import store.model.Store;
import store.view.InputView;
import store.view.OutputView;

import java.util.ArrayList;
import java.util.List;

public class DiscountService {
    private final InputView inputView;
    private final OutputView outputView;
    private final StoreConverter storeConverter;

    public DiscountService(InputView inputView,
                           OutputView outputView,
                           StoreConverter storeConverter) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.storeConverter = storeConverter;
    }

    public List<Product> checkPromotionDiscount(Store store, List<Product> products, Integer quantity) {
        Product product = products.get(0);
        Promotion promotion = store.getPromotionByName(product.getPromotion());

        if (promotion != null && promotion.validationNow()) {
            return promotionDiscount(products, quantity, promotion);
        }
        return createProducts(products.get(0), quantity, 0, "");
    }

    private List<Product> promotionDiscount(List<Product> products, Integer quantity, Promotion promotion) {
        if (!products.get(0).checkOutOfStock(quantity)) {
            return promotionOutOfStock(products.get(0), quantity, promotion);
        }
        if (promotion.checkAdditionalGet(quantity)) {
            quantity = promotionLowQuantity(products, quantity);
        }
        Integer get = promotion.getDiscountAmount(quantity);
        Integer buy = quantity - get;

        return createProducts(products.get(0), buy, get, promotion.getName());
    }

    private List<Product> createProducts(Product product, Integer buy, Integer get, String promotion) {
        Product buyProduct = new Product(product.getName(), product.getPrice(), buy, promotion, "N");
        List<Product> purchaseProducts = new ArrayList<>();

        purchaseProducts.add(buyProduct);
        if (get != 0) {
            purchaseProducts.add(new Product(product.getName(), product.getPrice(), get, promotion, "Y"));
        }

        return purchaseProducts;
    }

    private Integer promotionLowQuantity(List<Product> products, Integer quantity) {
        String productName = products.get(0).getName();

        if (getAddtionalProduct(productName)) {
            quantity += 1;
        }

        return quantity;
    }

    private boolean getAddtionalProduct(String productName) {
        while (true) {
            try {
                String guideMessage = GuideMessage.PROMOTION_LOW_QUANTITY_MESSAGE.formatMessage(productName, 1);
                String input = inputView.getYnInput(guideMessage);
                return storeConverter.converToBoolean(input);
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private List<Product> promotionOutOfStock(Product product, Integer quantity, Promotion promotion) {
        String productName = product.getName();
        Integer stockQuantity = product.getQuantity();
        Integer outOfStock = quantity - promotion.getPromotionAmount(stockQuantity);

        if (!getPromotionOutOfStock(productName, outOfStock)) {
            outOfStock = 0;
        }

        Integer get = promotion.getDiscountAmount(stockQuantity);
        Integer buy = quantity - stockQuantity + outOfStock - get;
        outOfStock = quantity - buy - get;

        return createOutOfStockProducts(product, buy, get, promotion.getName(), outOfStock);
    }

    private List<Product> createOutOfStockProducts(Product product, Integer buy, Integer get, String promotion, Integer outOfStock) {
        List<Product> outOfStockProducts = new ArrayList<>();

        outOfStockProducts.addAll(createProducts(product, buy, get, promotion));
        if (!outOfStock.equals(0)){
            outOfStockProducts.addAll(createProducts(product, outOfStock, 0, ""));
        }

        return outOfStockProducts;
    }


    private boolean getPromotionOutOfStock(String productName, Integer quantity) {
        while (true) {
            try {
                String guideMessage = GuideMessage.PROMOTION_OUT_OF_STOCK_MESSAGE.formatMessage(productName, quantity);
                String input = inputView.getYnInput(guideMessage);
                return storeConverter.converToBoolean(input);
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }
}
