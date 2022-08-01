package cn.holmes.settle.expression.lang.opt.custom;

import cn.holmes.settle.expression.common.element.Precision;
import cn.holmes.settle.expression.common.element.SettleDecimal;
import cn.holmes.settle.expression.lang.opt.RunMethod;

import java.math.RoundingMode;
import java.util.List;

/**
 * 五舍六入
 */
public class HalfDown implements RunMethod {

    @Override
    public Object run(List<Object> param) {
        if (param.isEmpty()) {
            return null;
        }
        SettleDecimal scale = (SettleDecimal) param.get(0);
        return new Precision(scale.intValue(), RoundingMode.HALF_DOWN);
    }

    @Override
    public String fetchSelf() {
        return "half_down";
    }
}
