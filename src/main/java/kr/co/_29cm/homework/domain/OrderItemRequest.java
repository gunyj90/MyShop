package kr.co._29cm.homework.domain;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class OrderItemRequest {

    private final Map<Long, Integer> itemQuantityCache = new HashMap<>();

    public OrderItemRequest add(Long itemId, int quantity) {
        itemQuantityCache.put(itemId, summarizeQuantity(itemId, quantity));
        return this;
    }

    public List<Long> getItemIds() {
        return new ArrayList<>(itemQuantityCache.keySet());
    }

    public int getQuantity(Long itemId) {
        return itemQuantityCache.get(itemId);
    }

    private int summarizeQuantity(Long itemId, int quantity) {
        Integer oldQuantity = itemQuantityCache.get(itemId);
        if (oldQuantity != null) {
            quantity = quantity + oldQuantity;
        }
        return quantity;
    }
}
