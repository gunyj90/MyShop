package kr.co._29cm.homework.domain;

import lombok.experimental.UtilityClass;
import org.springframework.util.StringUtils;

import java.text.DecimalFormat;
import java.util.Arrays;

@UtilityClass
public class NumericUtils {

    public static boolean isNotNumeric(String... stringNumbers) {
        if (stringNumbers == null ||
                Arrays.stream(stringNumbers)
                        .anyMatch(sn -> !StringUtils.hasText(sn))) {
            return true;
        }

        String numericPattern = "[0-9]*";

        for (String stringNumber : stringNumbers) {
            if (!stringNumber.matches(numericPattern)) {
                return true;
            }
        }

        return false;
    }

    public static String getPriceString(double price) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###,###");
        return decimalFormat.format(price) + "원";
    }
}
