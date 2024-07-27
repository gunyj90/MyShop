package kr.co._29cm.homework.domain;

import kr.co._29cm.homework.exception.ItemNotExistException;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    void 상품이_존재하지_않으면_주문할_수_없다() {
        //given
        List<OrderItem> orderItems = Collections.emptyList();

        //when
        //then
        assertThatThrownBy (()->Order.from(orderItems))
                .isInstanceOf(ItemNotExistException.class);
    }

    @Test
    void 주문금액은_주문상품_총금액의_합과_같다() {
        //given
        Item item1 = Item.builder().price(1000L).stock(10).build();
        Item item2 = Item.builder().price(2000L).stock(10).build();
        List<OrderItem> orderItems = List.of(
                OrderItem.of(item1, 3),
                OrderItem.of(item2, 2)
        );
        long totalOrderItemsPrice = orderItems.stream().mapToLong(OrderItem::getTotalPrice).sum();

        //when
        Order order = Order.from(orderItems);

        //then
        assertThat(order.getOrderPrice()).isEqualTo(totalOrderItemsPrice);
    }

    @Test
    void 주문금액이_50000원_미만일_경우_2500원의_배송비가_발생한다() {
        //given
        Item item1 = Item.builder().price(50_000L).stock(10).build();
        Item item2 = Item.builder().price(20_000L).stock(10).build();
        List<OrderItem> orderItems1 = List.of(OrderItem.of(item1, 1));
        List<OrderItem> orderItems2 = List.of(OrderItem.of(item2, 1));

        //when
        Order order1 = Order.from(orderItems1);
        Order order2 = Order.from(orderItems2);

        //then
        assertAll(
                ()->{
                    assertThat(order1.getDeliveryPrice()).isEqualTo(0);},
                ()->{
                    assertThat(order2.getDeliveryPrice()).isEqualTo(2500);}
        );
    }
}