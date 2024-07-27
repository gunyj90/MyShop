package kr.co._29cm.homework.service;

import kr.co._29cm.homework.exception.ItemNotExistException;
import kr.co._29cm.homework.repository.ItemRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@Import(ItemService.class)
class ItemServiceTest {

    @Autowired
    private ItemService itemService;

    @MockBean
    private ItemRepository itemRepository;

    @MockBean
    private DataReader dataReader;

    @Test
    void 상품이_존재하지_않으면_ItemNotExistException이_발생한다() {
        //given
        BDDMockito.when(itemRepository.findAllById(BDDMockito.any()))
                .thenReturn(Collections.emptyList());

        //when
        //then
        Assertions.assertThatThrownBy(()->itemService.getItemsByIds(null))
                .isInstanceOf(ItemNotExistException.class);
    }
}