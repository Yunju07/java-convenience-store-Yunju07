package store;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
}
