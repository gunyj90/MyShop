package kr.co._29cm.homework.service;

import kr.co._29cm.homework.domain.Item;
import kr.co._29cm.homework.domain.ItemDto;
import kr.co._29cm.homework.domain.OrderItemRequest;
import kr.co._29cm.homework.exception.ItemNotExistException;
import kr.co._29cm.homework.exception.SoldOutException;
import kr.co._29cm.homework.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class ItemService {

    private final ItemRepository itemRepository;
    private final DataReader dataReader;

    @Transactional()
    public void createItems() {
        if (hasAnyItem()) {
            return;
        }

        List<Item> items = dataReader.read(words ->
                Item.builder()
                        .id(Long.parseLong(words[0]))
                        .name(words[1])
                        .price(Long.parseLong(words[2]))
                        .stock(Integer.parseInt(words[3]))
                        .build());

        itemRepository.saveAll(items);
    }

    @Transactional(readOnly = true)
    public List<ItemDto> getAllItems() {
        return itemRepository.findAllByStockGreaterThan(0);
    }

    @Transactional(readOnly = true)
    public List<Item> getItemsByIds(List<Long> ids) {
        List<Item> items = itemRepository.findAllById(ids);
        if (items.isEmpty()) {
            throw new ItemNotExistException("상품이 존재하지 않습니다.");
        }
        return items;
    }

    public void decreaseItemsStock(List<Item> items, OrderItemRequest orderItemRequest) {
        items.stream()
                .mapToLong(Item::getId)
                .mapToInt(id -> itemRepository.updateStockByIdAndStockGreaterOrEqualThanQuantity(id, orderItemRequest.getQuantity(id)))
                .filter(updateCount -> updateCount == 0)
                .findAny()
                .ifPresent(errorFound -> {
                    throw new SoldOutException();
                });
    }

    private boolean hasAnyItem() {
        return itemRepository.existsBy();
    }
}
