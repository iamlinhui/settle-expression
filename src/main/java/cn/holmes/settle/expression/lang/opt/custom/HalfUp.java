package cn.holmes.settle.expression.lang.opt.custom;

import cn.holmes.settle.expression.common.converter.TypeConverter;
import cn.holmes.settle.expression.common.element.Precision;
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
        Integer scale = TypeConverter.convert(param.get(0), Integer.class);
        return new Precision(scale, RoundingMode.HALF_UP);
    }

    @Override
    public String fetchSelf() {
        return "half_up";
    }
}
