package kr.co._29cm.homework.domain;

import jakarta.persistence.*;
import kr.co._29cm.homework.exception.ItemNotExistException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Order extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    private Long orderPrice;

    private Long deliveryPrice;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    public static Order from(List<OrderItem> orderItems) {
        Order order = new Order();
        if (orderItems == null || orderItems.isEmpty()) {
            throw new ItemNotExistException("주문할 상품이 없습니다.");
        }
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.orderPrice = orderItems.stream().mapToLong(OrderItem::getTotalPrice).sum();
        order.deliveryPrice = order.calculateDeliveryPrice();
        return order;
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        if (orderItem.getOrder() == null) {
            orderItem.setOrder(this);
        }
    }

    public Long getTotalPrice() {
        return orderPrice + calculateDeliveryPrice();
    }

    private Long calculateDeliveryPrice() {
        return (long) (orderPrice < 50000 ? 2500 : 0);
    }

    public String toString() {
        return "주문 내역:\n"
                + "------------------------------------------\n"
                + orderItems.stream().map(Object::toString).collect(Collectors.joining("\n"))
                + "\n------------------------------------------\n"
                + "주문금액: " + NumericUtils.getPriceString(orderPrice) + "\n"
                + "배송비: " + NumericUtils.getPriceString(deliveryPrice) + "\n"
                + "------------------------------------------\n"
                + "지불금액: " + NumericUtils.getPriceString(getTotalPrice()) + "\n"
                + "------------------------------------------\n";
    }
}
