package kr.co._29cm.homework.runner;

import kr.co._29cm.homework.domain.NumericUtils;
import kr.co._29cm.homework.domain.Order;
import kr.co._29cm.homework.domain.OrderItemRequest;
import kr.co._29cm.homework.service.ItemService;
import kr.co._29cm.homework.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@RequiredArgsConstructor
@Component
public class ConsoleRunner implements CommandLineRunner {

    private final OrderService orderService;
    private final ItemService itemService;

    @Override
    public void run(String... args) {
        itemService.createItems();
        new Thread(this::handleConsoleInput).start();
    }

    private void handleConsoleInput() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("입력(o[order]: 주문, q[quit]: 종료) : ");
            String input = scanner.nextLine();

            if ("o".equals(input) || "order".equals(input)) {
                System.out.println("상품번호\t상품명\t\t\t\t\t판매가격\t재고수량");
                itemService.getAllItems().forEach(System.out::println);
                handleOrderProcess(scanner);
                continue;
            } else if ("q".equalsIgnoreCase(input.trim()) || "quit".equalsIgnoreCase(input.trim())) {
                System.out.println("고객님의 주문 감사합니다.");
                System.exit(0);
            }

            System.out.println("입력값이 유효하지 않습니다.");
        }
    }

    private void handleOrderProcess(Scanner scanner) {
        OrderItemRequest orderItemRequest = new OrderItemRequest();
        while (true) {
            System.out.print("상품번호: ");
            String prodId = scanner.nextLine();

            System.out.print("수량: ");
            String quantity = scanner.nextLine();

            if (" ".equals(prodId) || " ".equals(quantity)) {
                try {
                    Order order = orderService.order(orderItemRequest);
                    System.out.println(order.toString());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;
            } else if (NumericUtils.isNotNumeric(prodId, quantity)) {
                System.out.println("입력값이 유효하지 않습니다.");
            } else {
                orderItemRequest.add(Long.parseLong(prodId), Integer.parseInt(quantity));
            }
        }
    }
}
