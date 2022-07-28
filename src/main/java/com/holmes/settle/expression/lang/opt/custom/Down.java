package com.holmes.settle.expression.lang.opt.custom;

import com.holmes.settle.expression.common.element.Precision;
import com.holmes.settle.expression.common.element.SettleDecimal;
import com.holmes.settle.expression.lang.opt.RunMethod;

import java.math.RoundingMode;
import java.util.List;

/**
 * 向下取整
 */
public class Down implements RunMethod {

    @Override
    public Object run(List<Object> param) {
        if (param.isEmpty()) {
            return null;
        }
        SettleDecimal scale = (SettleDecimal) param.get(0);
        return new Precision(scale.intValue(), RoundingMode.DOWN);
    }

    @Override
    public String fetchSelf() {
        return "down";
    }
}
