package com.dshamc.settle.expression.lang.opt.custom;

import com.dshamc.settle.expression.common.element.Precision;
import com.dshamc.settle.expression.common.element.SettleDecimal;
import com.dshamc.settle.expression.lang.opt.RunMethod;

import java.math.RoundingMode;
import java.util.List;

/**
 * 四舍五入
 */
public class HalfUp implements RunMethod {

    @Override
    public Object run(List<Object> param) {
        if (param.size() <= 0) {
            return null;
        }
        SettleDecimal scale = (SettleDecimal) param.get(0);
        return new Precision(scale.getInner().intValue(), RoundingMode.HALF_UP);
    }

    @Override
    public String fetchSelf() {
        return "half_up";
    }
}
