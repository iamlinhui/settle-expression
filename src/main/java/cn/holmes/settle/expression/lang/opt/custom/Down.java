package cn.holmes.settle.expression.lang.opt.custom;

import cn.holmes.settle.expression.common.converter.TypeConverter;
import cn.holmes.settle.expression.common.element.Precision;
import cn.holmes.settle.expression.common.element.SettleDecimal;
import cn.holmes.settle.expression.lang.opt.RunMethod;

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
        Integer scale = TypeConverter.convert(param.get(0), Integer.class);
        return new Precision(scale, RoundingMode.DOWN);
    }

    @Override
    public String fetchSelf() {
        return "down";
    }
}
