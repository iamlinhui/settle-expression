package com.holmes.settle.expression.lang.opt.object;

import com.holmes.settle.expression.lang.opt.TwoTernary;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;

/**
 * 数组读取
 * 将'['做为读取操作符使用,它读取两个参数,一个是数组本身,一个是下标
 * 多维数组,则是先读出一维,然后再读取下一维度的数据
 */
public class ArrayOpt extends TwoTernary {
    public int fetchPriority() {
        return 1;
    }

    @SuppressWarnings("rawtypes")
    public Object calculate() {
        Object lval = calculateItem(left);
        Object rval = calculateItem(right);

        if (lval instanceof Map) {
            Map<?, ?> om = (Map<?, ?>) lval;
            return om.get(right.toString());
        } else if (lval instanceof List) {
            return ((List) lval).get(Integer.parseInt(rval.toString()));
        }
        return Array.get(lval, Integer.parseInt(rval.toString()));
    }

    public String fetchSelf() {
        return "[";
    }
}
