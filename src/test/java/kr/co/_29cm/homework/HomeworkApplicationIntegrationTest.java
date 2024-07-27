package kr.co._29cm.homework;

import kr.co._29cm.homework.domain.ItemDto;
import kr.co._29cm.homework.domain.OrderItemRequest;
import kr.co._29cm.homework.exception.SoldOutException;
import kr.co._29cm.homework.runner.ConsoleRunner;
import kr.co._29cm.homework.service.ItemService;
import kr.co._29cm.homework.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.ThrowableAssert.*;

@Transactional
@SpringBootTest(classes = HomeworkApplication.class)
class HomeworkApplicationIntegrationTest {

	@Autowired
	private OrderService orderService;

	@Autowired
	private ItemService itemService;

	@Autowired
	private RequiredNewProxy requiredNewProxy;

	@MockBean
	private ConsoleRunner consoleRunner;

	@Test
	void csv파일을_읽어_item을_저장한다() {
		//given
		itemService.createItems();

		//when
		List<ItemDto> items = itemService.getAllItems();

		//then
		assertThat(items).hasSizeGreaterThan(0);
	}

	@Test
	void 비동기적으로_주문시_재고가_부족할_경우_SoldOutException이_발생한다() {
		//given
		requiredNewProxy.run(()-> itemService.createItems());

		//when
		ThrowingCallable callable = () ->
				IntStream.rangeClosed(1, 10)
					.parallel()
					.forEach(_ -> requiredNewProxy.run(()-> {
						OrderItemRequest request = new OrderItemRequest();
						request.add(123456L, 10);
						orderService.order(request);
					}));

		//then
		assertThatThrownBy(callable).isInstanceOf(SoldOutException.class);
	}

	@Test
	void 한번에_2개_이상의_상품을_요청하여_주문할_수_있다() {

	}

}
