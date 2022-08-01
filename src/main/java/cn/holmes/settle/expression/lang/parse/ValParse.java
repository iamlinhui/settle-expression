package cn.holmes.settle.expression.lang.parse;

import cn.holmes.settle.expression.common.element.SettleDecimal;

import java.util.Arrays;
import java.util.List;

/**
 * 数值转换器
 */
public class ValParse implements Parse {

    private static final List<Character> NUMBER = Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');

    @Override
    public Object fetchItem(CharQueue exp) {
        StringBuilder numberBuilder = new StringBuilder();
        switch (exp.peek()) {
            case '.':
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                boolean hasPoint = exp.peek() == '.';
                numberBuilder.append(exp.poll());
                while (!exp.isEmpty()) {
                    switch (exp.peek()) {
                        case '0':
                        case '1':
                        case '2':
                        case '3':
                        case '4':
                        case '5':
                        case '6':
                        case '7':
                        case '8':
                        case '9':
                            numberBuilder.append(exp.poll());
                            break;
                        case '.':
                            if (hasPoint) {
                                return SettleDecimal.warp(numberBuilder.toString());
                            }
                            hasPoint = true;
                            char next = exp.peek(1);
                            if (!NUMBER.contains(next)) {
                                return SettleDecimal.warp(numberBuilder.toString());
                            }
                            numberBuilder.append(exp.poll());
                            break;
                        default:
                            return SettleDecimal.warp(numberBuilder.toString());
                    }
                }
                return SettleDecimal.warp(numberBuilder.toString());
            default:
        }
        return NULL;
    }

}
