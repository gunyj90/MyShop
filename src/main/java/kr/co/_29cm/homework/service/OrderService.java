package kr.co._29cm.homework.service;

import kr.co._29cm.homework.domain.Item;
import kr.co._29cm.homework.domain.Order;
import kr.co._29cm.homework.domain.OrderItem;
import kr.co._29cm.homework.domain.OrderItemRequest;
import kr.co._29cm.homework.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional()
public class OrderService {

    private final ItemService itemService;
    private final OrderRepository orderRepository;

    public Order order(OrderItemRequest orderItemRequest) {
        List<Item> items = itemService.getItemsByIds(orderItemRequest.getItemIds());
        List<OrderItem> orderItems = items.stream()
                .map(item -> OrderItem.of(item, orderItemRequest.getQuantity(item.getId())))
                .toList();

        Order order = Order.from(orderItems);
        orderRepository.save(order);

        itemService.decreaseItemsStock(items, orderItemRequest);
        return order;
    }
}
