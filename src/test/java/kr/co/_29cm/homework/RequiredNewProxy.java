package kr.co._29cm.homework;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class RequiredNewProxy {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    void run(Runnable runnable) {
        runnable.run();
    }
}
