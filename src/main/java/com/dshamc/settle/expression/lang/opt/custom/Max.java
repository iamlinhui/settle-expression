package com.dshamc.settle.expression.lang.opt.custom;

import com.dshamc.settle.expression.common.element.SettleDecimal;
import com.dshamc.settle.expression.lang.opt.RunMethod;

import java.util.List;

/**
 * 取大
 */
public class Max implements RunMethod {

    public Object run(List<Object> param) {
        if (param.isEmpty()) {
            return null;
        }
        SettleDecimal n1 = (SettleDecimal) param.get(0);
        for (int i = 1; i < param.size(); i++) {
            SettleDecimal n2 = (SettleDecimal) param.get(i);
            n1 = n1.max(n2);
        }
        return n1;
    }

    public String fetchSelf() {
        return "max";
    }

}
