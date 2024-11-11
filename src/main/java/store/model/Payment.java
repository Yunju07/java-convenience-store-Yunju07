package store.model;

import store.enums.Membership;

import java.util.List;

public class Payment {
    private Integer totalProductsAmount;
    private Integer promotionDiscount;
    private Integer membershipDiscount;
    private Integer totalPaymentAmount;

    public Payment() {
        this.totalProductsAmount = 0;
        this.promotionDiscount = 0;
        this.membershipDiscount = 0;
        this.totalPaymentAmount = 0;
    }

    public Integer getTotalProductsAmount() {
        return totalProductsAmount;
    }

    public Integer getPromotionDiscount() {
        return promotionDiscount;
    }

    public Integer getMembershipDiscount() {
        return membershipDiscount;
    }

    public Integer getTotalPaymentAmount() {
        return totalPaymentAmount;
    }

    public void setTotalProductsAmount(List<Product> totalProducts) {
        for (Product product : totalProducts) {
            totalProductsAmount += product.getPrice() * product.getQuantity();
        }
    }

    public void setPromotionDiscount(List<Product> giftProducts) {
        for (Product product : giftProducts) {
            promotionDiscount += product.getPrice() * product.getQuantity();
        }
    }

    public void setMembershipDiscount(List<Product> totalProducts, Membership membership) {
        for (Product product : totalProducts) {
            if (product.getPromotion().equals("")){
                membershipDiscount += product.getPrice() * product.getQuantity();
            }
        }
        if (membership != null) {
            discountMembership(membership);
            return;
        }
        membershipDiscount = 0;
    }

    public void setTotalPaymentAmount() {
        totalPaymentAmount = totalProductsAmount - promotionDiscount - membershipDiscount;
    }

    private void discountMembership(Membership membership) {
        double discountRate = membership.getDiscountRate();
        int discountLimit = membership.getDiscountLimit();
        membershipDiscount = (int) (membershipDiscount * discountRate);

        if (discountLimit < membershipDiscount) {
            membershipDiscount = discountLimit;
        }
    }
}
