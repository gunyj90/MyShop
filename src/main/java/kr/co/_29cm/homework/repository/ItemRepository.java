package kr.co._29cm.homework.repository;

import kr.co._29cm.homework.domain.Item;
import kr.co._29cm.homework.domain.ItemDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    List<ItemDto> findAllByStockGreaterThan(int stock);

    boolean existsBy();

    @Modifying
    @Query("update Item set stock = stock - :quantity" +
            ", updatedAt = current_timestamp" +
            " where id = :id and stock >= :quantity")
    int updateStockByIdAndStockGreaterOrEqualThanQuantity(Long id, int quantity);
}
