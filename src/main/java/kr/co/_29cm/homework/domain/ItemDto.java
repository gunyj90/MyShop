package kr.co._29cm.homework.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ItemDto {
    private Long id;

    private String name;

    private Long price;

    private int stock;

    public String toString() {
        return String.format("%d\t%s\t\t\t\t\t%d\t%d", id, name, price, stock);
    }
}
