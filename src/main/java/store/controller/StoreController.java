package store.controller;

import store.converter.StoreConverter;
import store.dto.CurrentStrockDTO;
import store.dto.ProductDTO;
import store.dto.ReceiptDTO;
import store.enums.GuideMessage;
import store.enums.Membership;
import store.model.*;
import store.service.DiscountService;
import store.service.PaymentService;
import store.service.StoreService;
import store.view.InputView;
import store.view.OutputView;

import java.util.ArrayList;
import java.util.List;

public class StoreController {
    private final InputView inputView;
    private final OutputView outputView;
    private final StoreConverter storeConverter;
    private final StoreService storeService;
    private final DiscountService discountService;
    private final PaymentService paymentService;

    public StoreController(InputView inputView,
                           OutputView outputView,
                           StoreConverter storeConverter,
                           StoreService storeService,
                           PaymentService paymentService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.storeConverter = storeConverter;
        this.storeService = storeService;
        this.paymentService = paymentService;
        this.discountService = new DiscountService(inputView, outputView, storeConverter);
    }
    public void start() {
        Store store = storeService.loadStoreInfo();

        while (true) {
            informCurrentStock(store.getStock());
            if (!purchase(store)) {
                break;
            }
        }
    }

    private void informCurrentStock(List<Product> stock) {
        // 환영 인사 및 재고 안내
        CurrentStrockDTO dto = storeConverter.convertToCurrentProductDTO(stock);
        outputView.printCurrentProducts(dto);
    }

    private boolean purchase(Store store) {
        List<ProductDTO> purchase = inputProductPurchase(store);

        // 프로모션 & 멤버쉽 할인 안내
        List<Product> purcasedProducts = guidePromotionDiscount(store, purchase);
        Membership membership = guideMembershipDiscount(store);

        storeService.purchaseProducts(store, purcasedProducts);
        payment(purcasedProducts, membership);

        return inputAdditionalPurchase();
    }

    private List<ProductDTO> inputProductPurchase(Store store) {
        while (true) {
            try {
                String input = inputView.getProducts();
                List<ProductDTO> purchase = storeConverter.convertToProducts(input);
                storeService.checkPurchasePossible(store, purchase);
                return purchase;
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private List<Product> guidePromotionDiscount(Store store, List<ProductDTO> purchase) {
        List<Product> products = new ArrayList<>();
        for (ProductDTO product : purchase) {
            List<Product> sameProduct = store.getProductsByName(product.name);
            List<Product> promotionProduct = discountService.checkPromotionDiscount(store, sameProduct, product.quantity);
            products.addAll(promotionProduct);
        }
        return products;
    }

    private Membership guideMembershipDiscount(Store store) {
        if (inputMembership()) {
            return store.getMembership();
        }
        return null;
    }

    private boolean inputMembership() {
        while (true) {
            try {
                String guideMessage = GuideMessage.MEMBERSHIP_MESSAGE.getMessage();
                String input = inputView.getYnInput(guideMessage);
                return storeConverter.converToBoolean(input);
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private boolean inputAdditionalPurchase() {
        while (true) {
            try {
                String guideMessage = GuideMessage.ADDITIONAL_PURCHASE_MESSAGE.getMessage();
                String input = inputView.getYnInput(guideMessage);
                return storeConverter.converToBoolean(input);
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private void payment(List<Product> purchasedProducts, Membership membership) {
        Payment payment = paymentService.createPayment(purchasedProducts, membership);

        Receipt receipt = paymentService.createReceipt(purchasedProducts, payment);
        ReceiptDTO receiptDTO = storeConverter.convertToReciptDTO(receipt);

        outputView.printReceipt(receiptDTO);
    }
}
