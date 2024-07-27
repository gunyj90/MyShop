package kr.co._29cm.homework.domain;

import kr.co._29cm.homework.exception.InvalidRequestException;
import kr.co._29cm.homework.exception.SoldOutException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class OrderItemTest {

    @Test
    void 상품의_남은_재고보다_큰_수량을_요청하면_SoldOutException이_발생한다() {
        //given
        Item item = Item.builder().price(1000L).stock(1).build();

        //when
        //then
        assertThatThrownBy(()->{OrderItem.of(item, 2);})
                .isInstanceOf(SoldOutException.class);
    }

    @Test
    void 주문상품_총가격은_상품가격과_요청수량의_곱과_같다() {
        //given
        Item item = Item.builder().price(1000L).stock(10).build();

        //when
        OrderItem orderItem = OrderItem.of(item, 2);

        //then
        assertThat(orderItem.getTotalPrice()).isEqualTo(1000L*2);
    }

    @Test
    void 주문상품의_요청수량은_0보다_커야한다() {
        //given
        Item item = Item.builder().price(1000L).stock(1).build();

        //when
        //then
        assertThatThrownBy(()->{OrderItem.of(item, 0);})
                .isInstanceOf(InvalidRequestException.class);
    }
}