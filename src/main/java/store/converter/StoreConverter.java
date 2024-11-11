package store.converter;

import store.dto.CurrentStrockDTO;
import store.dto.PaymentDTO;
import store.dto.ProductDTO;
import store.dto.ReceiptDTO;
import store.enums.ErrorMessage;
import store.model.Payment;
import store.model.Product;
import store.model.Receipt;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StoreConverter {
    private static final String PRODUCT_REGEX = "^\\[(\\S+)-(\\d+)\\]$";

    public CurrentStrockDTO convertToCurrentProductDTO(List<Product> products) {
        List<ProductDTO> productDTOS = new ArrayList<>();
        for (Product product : products) {
            String name = product.getName();
            Integer price = product.getPrice();
            Integer quantity = product.getQuantity();
            String promotion = product.getPromotion();
            productDTOS.add(new ProductDTO(name, price, quantity, promotion));
        }

        return new CurrentStrockDTO(productDTOS);
    }

    public List<ProductDTO> convertToProducts(String input) {
        String[] productsInputs = input.split(",");
        List<ProductDTO> products = new ArrayList<>();
        for (String product : productsInputs) {
            Matcher matcher = Pattern.compile(PRODUCT_REGEX).matcher(product);
            if (!matcher.matches()) {
                throw new IllegalArgumentException(ErrorMessage.INVALID_PRODUCT_QUANTITY_ERROR.getMessage());
            }
            products.add(new ProductDTO(matcher.group(1), Integer.parseInt(matcher.group(2))));
        }
        return products;
    }

    public boolean converToBoolean(String input) {
        if (input.equals("Y")) {
            return true;
        }
        if (input.equals("N")) {
            return false;
        }
        String errorMessage = ErrorMessage.OTHER_ERROR.getMessage();
        throw new IllegalArgumentException(errorMessage);
    }

    public ReceiptDTO convertToReciptDTO(Receipt receipt){
        List<ProductDTO> totalProductDTOS = convertToProductDTO(receipt.getTotalProducts());
        List<ProductDTO> giftProductDTOS = convertToProductDTO(receipt.getGiftProducts());
        PaymentDTO paymentDTO = convertToPaymentDTO(receipt.getPayment());

        return new ReceiptDTO(totalProductDTOS, giftProductDTOS, paymentDTO);
    }

    private List<ProductDTO> convertToProductDTO(List<Product> products) {
        List<ProductDTO> dtos = new ArrayList<>();
        for (Product product : products) {
            ProductDTO dto = new ProductDTO(product.getName(),
                    product.getPrice(),
                    product.getQuantity(),
                    null);
            dtos.add(dto);
        }
        return dtos;
    }

    private PaymentDTO convertToPaymentDTO(Payment payment) {
        return new PaymentDTO(payment.getTotalProductsAmount(),
                payment.getPromotionDiscount(),
                payment.getMembershipDiscount(),
                payment.getTotalPaymentAmount());
    }
}
