package store.dto;

public class PaymentDTO {
    public Integer totalProductsAmount;
    public Integer promotionDiscount;
    public Integer membershipDiscount;
    public Integer totalPaymentAmount;

    public PaymentDTO(Integer totalProductsAmount,
                      Integer promotionDiscount,
                      Integer membershipDiscount,
                      Integer totalPaymentAmount) {
        this.totalProductsAmount = totalProductsAmount;
        this.promotionDiscount = promotionDiscount;
        this.membershipDiscount = membershipDiscount;
        this.totalPaymentAmount = totalPaymentAmount;
    }
}
