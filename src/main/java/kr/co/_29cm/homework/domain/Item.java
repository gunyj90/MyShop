package kr.co._29cm.homework.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.domain.Persistable;

@Getter
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Item extends AbstractEntity implements Persistable<Long> {

    @Id
    private Long id;

    private String name;

    private Long price;

    private int stock;

    @Builder
    private Item(Long id, String name, Long price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    @Override
    public boolean isNew() {
        return createdAt == null;
    }
}
