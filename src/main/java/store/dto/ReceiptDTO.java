package store.dto;

import java.util.List;

public class ReceiptDTO {
    private List<ProductDTO> totalProdutDTOS;
    private List<ProductDTO> giftProductDTOS;
    private PaymentDTO paymentDTO;

    public ReceiptDTO(List<ProductDTO> totalProdutDTOS,
                      List<ProductDTO> giftProductDTOS,
                      PaymentDTO paymentDTO) {
        this.totalProdutDTOS = totalProdutDTOS;
        this.giftProductDTOS = giftProductDTOS;
        this.paymentDTO = paymentDTO;
    }

    public List<ProductDTO> getTotalProdutDTOS() {
        return totalProdutDTOS;
    }

    public List<ProductDTO> getGiftProductDTOS() {
        return giftProductDTOS;
    }

    public PaymentDTO getPaymentDTO() {
        return paymentDTO;
    }
}
