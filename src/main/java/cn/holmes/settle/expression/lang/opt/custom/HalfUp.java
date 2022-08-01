package cn.holmes.settle.expression.lang.opt.custom;

import cn.holmes.settle.expression.common.element.Precision;
import cn.holmes.settle.expression.common.element.SettleDecimal;
import cn.holmes.settle.expression.lang.opt.RunMethod;

import java.math.RoundingMode;
import java.util.List;

/**
 * 四舍五入
 */
public class HalfUp implements RunMethod {

    @Override
    public Object run(List<Object> param) {
        if (param.isEmpty()) {
            return null;
        }
        SettleDecimal scale = (SettleDecimal) param.get(0);
        return new Precision(scale.intValue(), RoundingMode.HALF_UP);
    }

    @Override
    public String fetchSelf() {
        return "half_up";
    }
}
