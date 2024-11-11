package store.model;

import camp.nextstep.edu.missionutils.DateTimes;

import java.time.LocalDate;

public class Promotion {
    private final String name;
    private final Integer buy;
    private final Integer get;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Promotion(String name,
                     Integer buy,
                     Integer get,
                     LocalDate startDate,
                     LocalDate endDate) {
        this.name = name;
        this.buy = buy;
        this.get = get;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public Integer getBuyCount(Integer getCount) {
        return (getCount / get) * buy;
    }

    public Integer getDiscountAmount(Integer totalAmount) {
        int discount = totalAmount / (buy + get);
        discount *= get;

        return discount;
    }

    public Integer getPromotionAmount(Integer totalAmount) {
        int discount = totalAmount / (buy + get);
        discount *= (buy + get);

        return discount;
    }

    public boolean checkAdditionalGet(Integer totalAmount) {
        return totalAmount % (buy + get) == buy;
    }

    public boolean validationNow() {
        LocalDate now = DateTimes.now().toLocalDate();
        if (now.isBefore(startDate) || now.isAfter(endDate)) {
            return false;
        }
        return true;
    }
}
