package kr.co._29cm.homework.infrastructure;

import kr.co._29cm.homework.domain.Item;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@Import(CsvDataReader.class)
class CsvDataReaderTest {

    @Autowired
    CsvDataReader csvDataReader;

    @Test
    void resources하위_readable안_csv파일을_읽고_Item으로_변환할_수_있다() {
        //given
        //when
        List<Item> list = csvDataReader.read(words->
                Item.builder().id(Long.parseLong(words[0])).build());

        //then
        assertAll(
                () -> assertThat(list.size()).isGreaterThan(0),
                () -> assertThat(list.getFirst()).isNotNull(),
                () -> assertThat(list.getFirst().getId()).isEqualTo(123456L)
        );
    }
}