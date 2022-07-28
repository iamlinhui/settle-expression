package com.dshamc.settle.expression.lang.parse;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Reader;
import java.util.LinkedList;

/**
 * 字符队列默认实现.
 */
public class CharQueueDefault implements CharQueue {

    private final Logger logger = LoggerFactory.getLogger(CharQueueDefault.class);

    private final Reader reader;
    private final LinkedList<Integer> cache;
    private int cursor;

    public CharQueueDefault(Reader reader) {
        this.reader = reader;
        cache = new LinkedList<>();
        try {
            cursor = reader.read();
        } catch (IOException e) {
            logger.debug("read error", e);
        }
    }

    public char peek() {
        return (char) cursor;
    }

    public char peek(int offset) {
        if (offset == 0) {
            return (char) cursor;
        }
        //这个地方因为已经预读了cursor 所以,偏移量要向后移动一位
        if (cache.size() > offset - 1) {
            return (char) cache.get(offset - 1).intValue();
        }
        int t = 0;
        for (int i = 0; i < offset - cache.size(); i++) {
            try {
                t = reader.read();
                cache.add(t);
            } catch (IOException e) {
                logger.debug("read error", e);
            }
        }
        return (char) t;
    }

    public char poll() {
        char x = (char) cursor;
        try {
            if (cache.isEmpty()) {
                cursor = reader.read();
            } else {
                cursor = cache.poll();
            }
        } catch (IOException e) {
            logger.debug("read error", e);
        }
        return x;
    }

    public boolean isEmpty() {
        return cursor == -1;
    }

}
