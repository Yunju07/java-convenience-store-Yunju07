package store;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.model.Product;
import store.model.Store;
import store.service.StoreService;


import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class StoreTest {
    private final StoreService storeService = new StoreService();
    private Store store = storeService.loadStoreInfo();

    @DisplayName("재고 정보 (products.md) 로드 확인")
    @Test
    void 재고_정보_로드_확인() {
        assertSimpleTest(() ->
                assertThat(store.checkProduct("콜라"))
                .isTrue());
        assertSimpleTest(() ->
                assertThat(store.checkProduct("제로콜라"))
                .isFalse());
    }

    @DisplayName("프로모션 정보 (promotions.md) 로드 확인")
    @Test
    void 프로모션_정보_로드_확인() {
        assertSimpleTest(() ->
                assertThat(store.getPromotionByName("탄산2+1").getName())
                .isEqualTo("탄산2+1"));
        assertSimpleTest(() ->
                assertThat(store.getPromotionByName("탄산3+2"))
                        .isEqualTo(null));
    }

    @DisplayName("제품이 존재하는 지 확인")
    @Test
    void 제품이_존재하는_지_확인() {
        String productName = "사이다";
        assertSimpleTest(() ->
                assertThat(store.checkProduct(productName))
                        .isTrue());
    }

    @DisplayName("재고가 충분한 지 확인")
    @Test
    void 재고가_충분한_지_확인() {
        String productName = "사이다";
        assertSimpleTest(() ->
                assertThat(store.checkStock(productName, 1))
                        .isTrue());
        assertSimpleTest(() ->
                assertThat(store.checkStock(productName, 16))
                        .isFalse());
    }

    @DisplayName("제품 구매에 따른 재고 업데이트 확인")
    @Test
    void 재고_업데이트_확인() {
        String productName = "물";
        Product product = new Product(productName, 500, 10, "");
        assertSimpleTest(() -> {
            store.purchase(product);
            assertThat(store.checkStock(productName, 1))
                    .isFalse();
        });
    }
}
