package store.view;

import camp.nextstep.edu.missionutils.Console;
import store.enums.GuideMessage;

public class InputView {
    public String getProducts() {
        System.out.println(GuideMessage.PURCHASE_PRODUCTS_MESSAGE.getMessage());
        String input = Console.readLine();
        input = input.replaceAll(" ", "");
        System.out.println("");

        return input;
    }

    public String getYnInput(String guideMessage) {
        System.out.println(guideMessage);
        String input = Console.readLine();
        input = input.replaceAll(" ", "");
        System.out.println("");

        return input;
    }
}
