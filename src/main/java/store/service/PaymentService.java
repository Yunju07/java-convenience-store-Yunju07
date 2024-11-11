package store.service;

import store.enums.Membership;
import store.model.Payment;
import store.model.Product;
import store.model.Receipt;

import java.util.ArrayList;
import java.util.List;

public class PaymentService {
    public Payment createPayment(List<Product> products, Membership membership) {
        List<Product> totalProducts = findTotalProducts(products);
        List<Product> giftProducts = findGiftProducts(products);

        Payment payment = new Payment();
        payment.setTotalProductsAmount(totalProducts);
        payment.setPromotionDiscount(giftProducts);
        payment.setMembershipDiscount(totalProducts, membership);
        payment.setTotalPaymentAmount();

        return payment;
    }

    public Receipt createReceipt(List<Product> products, Payment payment) {
        List<Product> totalProducts = findTotalProducts(products);
        List<Product> giftProducts = findGiftProducts(products);
        return new Receipt(totalProducts, giftProducts, payment);
    }

    private List<Product> findTotalProducts(List<Product> products) {
        List<Product> totalProducts = new ArrayList<>();
        for (Product product : products) {
            Product addProduct = addProduct(totalProducts, product);
            if(addProduct != null) {
                totalProducts.add(addProduct);
            }
        }
        return totalProducts;
    }

    private Product addProduct(List<Product> totalProducts, Product product) {
        for (Product existedProduct : totalProducts) {
            if (existedProduct.getName().equals(product.getName())) {
                addExistedProduct(existedProduct, product);
                return null;
            }
        }
        return new Product(product.getName(),
                product.getPrice(),
                product.getQuantity(),
                product.getPromotion(),
                product.getGetYn());
    }

    private void addExistedProduct(Product existedProduct, Product addProduct) {
        Integer quantity = addProduct.getQuantity();
        existedProduct.increaseQuantity(quantity);

        String promotion = addProduct.getPromotion();
        if (!promotion.equals("")) {
            existedProduct.setPromotion(promotion);
        }
    }

    private List<Product> findGiftProducts(List<Product> products) {
        List<Product> giftProducts = new ArrayList<>();
        for (Product product : products) {
            if (product.getGetYn().equals("Y")) {
                giftProducts.add(product);
            }
        }
        return giftProducts;
    }
}
