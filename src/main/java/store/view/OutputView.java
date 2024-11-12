package store.view;

import store.dto.CurrentStrockDTO;
import store.dto.PaymentDTO;
import store.dto.ProductDTO;
import store.dto.ReceiptDTO;
import store.enums.GuideMessage;

import java.util.List;

public class OutputView {
    public void printCurrentProducts(CurrentStrockDTO currentProductDTO) {
        System.out.println(GuideMessage.WELCOME_CURRENT_PRODUCTS_MESSAGE.getMessage()+"\n");

        List<ProductDTO> productDTOS = currentProductDTO.getProductDTOS();
        for (ProductDTO dto : productDTOS) {
            String productMessage = formatProductMessage(dto);
            System.out.println(productMessage);
        }
        System.out.println("");
    }

    public void printErrorMessage(String message) {
        System.out.println(message+"\n");
    }

    public void printReceipt(ReceiptDTO receiptDTO) {
        printTotalProducts(receiptDTO.getTotalProdutDTOS());
        printGiftProducts(receiptDTO.getGiftProductDTOS());

        Integer totalCount = calculateTotalCount(receiptDTO.getTotalProdutDTOS());
        printPayment(totalCount, receiptDTO.getPaymentDTO());
        System.out.println("");
    }

    private void printTotalProducts(List<ProductDTO> productDTOS) {
        System.out.println("==============W 편의점================");
        System.out.println("상품명\t\t수량\t금액");
        for (ProductDTO dto : productDTOS) {
            int totalPrice = dto.price * dto.quantity;
            String message = String.format("%s\t\t%d \t%,d", dto.name, dto.quantity, totalPrice);
            System.out.println(message);
        }
    }

    private void printGiftProducts(List<ProductDTO> productDTOS) {
        System.out.println("=============증\t정===============");
        for (ProductDTO dto : productDTOS) {
            String message = String.format("%s\t\t%d", dto.name, dto.quantity);
            System.out.println(message);
        }
    }

    private void printPayment(Integer totalCount, PaymentDTO paymentDTO) {
        System.out.println("====================================");
        System.out.println(String.format("총구매액\t\t%d\t%,d", totalCount, paymentDTO.totalProductsAmount));
        System.out.println(String.format("행사할인\t\t\t-%,d", paymentDTO.promotionDiscount));
        System.out.println(String.format("멤버십할인\t\t\t-%,d", paymentDTO.membershipDiscount));
        System.out.println(String.format("내실돈\t\t\t %,d", paymentDTO.totalPaymentAmount));
    }

    private Integer calculateTotalCount(List<ProductDTO> productDTOS) {
        int totalCount = 0 ;
        for (ProductDTO dto : productDTOS) {
            totalCount += dto.quantity;
        }

        return totalCount;
    }

    private String formatProductMessage(ProductDTO dto) {
        String quantity = dto.quantity.toString() + "개";

        if (quantity.equals("0개")) {
            quantity = "재고 없음";
        }
        String message = String.format("- %s %,d원 %s %s", dto.name, dto.price, quantity, dto.promotion);

        return message;
    }
}
