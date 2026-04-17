package cn.holmes.settle.expression.lang.parse;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Reader;

/**
 * 字符队列默认实现. 基于char数组，避免Reader和LinkedList的开销
 */
public class CharQueueDefault implements CharQueue {

    private final Logger logger = LoggerFactory.getLogger(CharQueueDefault.class);

    private final char[] chars;
    private int cursor;
    private final int length;

    public CharQueueDefault(Reader reader) {
        StringBuilder sb = new StringBuilder();
        try {
            int c;
            while ((c = reader.read()) != -1) {
                sb.append((char) c);
            }
        } catch (IOException e) {
            logger.debug("read error", e);
        }
        this.chars = sb.toString().toCharArray();
        this.cursor = 0;
        this.length = chars.length;
    }

    public char peek() {
        return chars[cursor];
    }

    public char peek(int offset) {
        int index = cursor + offset;
        if (index >= length) {
            return (char) -1;
        }
        return chars[index];
    }

    public char poll() {
        return chars[cursor++];
    }

    public boolean isEmpty() {
        return cursor >= length;
    }

}
