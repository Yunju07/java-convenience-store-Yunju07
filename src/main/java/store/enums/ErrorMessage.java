package store.enums;

public enum ErrorMessage {
    INVALID_PRODUCT_QUANTITY_ERROR("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    NONEXISTENT_PRODUCT_ERROR("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요."),
    OUT_OF_STOCK_ERROR("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."),
    OTHER_ERROR("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.");

    private final String message;

    private ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
