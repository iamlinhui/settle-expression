package com.dshamc.settle.expression.el.parse;

import com.dshamc.settle.expression.common.ElException;
import com.dshamc.settle.expression.common.element.SettleDecimal;

import java.util.regex.Pattern;

/**
 * 字符串转换器
 */
public class StringParse implements Parse {

    private static final Pattern NUMBER_REGEX = Pattern.compile("^[-+]?([0-9]+)([.]([0-9]+))?$");

    @Override
    public Object fetchItem(CharQueue exp) {
        switch (exp.peek()) {
            case '\'':
            case '"':
                StringBuilder sb = new StringBuilder();
                char end = exp.poll();
                while (!exp.isEmpty() && exp.peek() != end) {
                    // 转义字符
                    if (exp.peek() == '\\') {
                        parseSp(exp, sb);
                    } else {
                        sb.append(exp.poll());
                    }
                }
                exp.poll();
                if (NUMBER_REGEX.matcher(sb).matches()) {
                    return SettleDecimal.warp(sb.toString());
                }
                return sb.toString();
            default:
        }
        return NULL;
    }

    private void parseSp(CharQueue exp, StringBuilder sb) {
        switch (exp.poll()) {
            case 'n':
                sb.append('\n');
                break;
            case 'r':
                sb.append('\r');
                break;
            case 't':
                sb.append('\t');
                break;
            case '\\':
                sb.append('\\');
                break;
            case '\'':
                sb.append('\'');
                break;
            case '\"':
                sb.append('\"');
                break;
            case 'u':
                char[] hex = new char[4];
                for (int i = 0; i < 4; i++) {
                    hex[i] = exp.poll();
                }
                sb.append((char) Integer.valueOf(new String(hex), 16).intValue());
                break;
            case 'b':
                //空格
                sb.append(' ');
                break;
            case 'f':
                //这个支持一下又何妨
                sb.append('\f');
                break;
            default:
                throw new ElException("Unexpected char");
        }
    }

}
