package kr.co._29cm.homework.domain;

import jakarta.persistence.*;
import kr.co._29cm.homework.exception.InvalidRequestException;
import kr.co._29cm.homework.exception.SoldOutException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class OrderItem extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long price; //현재상품가격과 구매당시 상품가격이 상이할 수 있음

    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    public static OrderItem of(Item item, int quantity) {
        OrderItem orderItem = new OrderItem();
        orderItem.item = item;
        orderItem.quantity = quantity;
        orderItem.price = item.getPrice();

        if (orderItem.isQuantityGreaterThan(item)) {
            throw new SoldOutException();
        }

        if (quantity <= 0) {
            throw new InvalidRequestException("요청수량은 0보다 커야합니다.");
        }

        return orderItem;
    }

    public Long getTotalPrice() {
        return price * quantity;
    }

    public void setOrder(Order order) {
        this.order = order;
        if (!order.getOrderItems().contains(this)) {
            order.getOrderItems().add(this);
        }
    }

    public String toString() {
        return String.format("%s - %d개", item.getName(), quantity);
    }

    private boolean isQuantityGreaterThan(Item item) {
        return quantity > item.getStock();
    }
}
