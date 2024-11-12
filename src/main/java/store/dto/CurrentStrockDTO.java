package store.dto;

import java.util.List;

public class CurrentStrockDTO {
    private List<ProductDTO> productDTOS;

    public CurrentStrockDTO(List<ProductDTO> productDTOS) {
        this.productDTOS = productDTOS;
    }

    public List<ProductDTO> getProductDTOS() {
        return productDTOS;
    }
}
