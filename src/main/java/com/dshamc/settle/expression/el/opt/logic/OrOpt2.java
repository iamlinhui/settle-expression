package com.dshamc.settle.expression.el.opt.logic;

import com.dshamc.settle.expression.common.Lang;
import com.dshamc.settle.expression.el.opt.TwoTernary;

/**
 * A ||| B 如果A是null, 或者A是数组/集合/Map但长度是0,返回B
 */
public class OrOpt2 extends TwoTernary {

    public int fetchPriority() {
        return 12;
    }

    public Object calculate() {
        Object lval = calculateItem(left);
        if (Lang.eleSize(lval) > 0) {
            return lval;
        }
        return calculateItem(right);
    }

    public String fetchSelf() {
        return "|||";
    }

}
