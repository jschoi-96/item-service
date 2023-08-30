package hello.itemservice.domain.item;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

// @Data -> 핵심 도메인 모델에 사용하기엔 위험한 부분이 있다
@Getter @Setter
public class Item {

    private Long id;
    private String itemName;
    private Integer price;
    private Integer quantity;
    // Integer로 설정하는 이유 -> price나 quantity 값이 null일때 방지

    public Item() {

    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
