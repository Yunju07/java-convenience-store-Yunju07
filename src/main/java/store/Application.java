package store;

import store.controller.StoreController;
import store.converter.StoreConverter;
import store.service.PaymentService;
import store.service.StoreService;
import store.view.InputView;
import store.view.OutputView;

public class Application {
    public static void main(String[] args) {
        // TODO: 프로그램 구현
        StoreController storeController = new StoreController(
                new InputView(),
                new OutputView(),
                new StoreConverter(),
                new StoreService(),
                new PaymentService()
        );
        storeController.start();
    }
}
