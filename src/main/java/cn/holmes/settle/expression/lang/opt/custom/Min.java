package cn.holmes.settle.expression.lang.opt.custom;

import cn.holmes.settle.expression.common.converter.TypeConverter;
import cn.holmes.settle.expression.common.element.SettleDecimal;
import cn.holmes.settle.expression.lang.opt.RunMethod;

import java.util.List;

/**
 * 取小
 */
public class Min implements RunMethod {
    public Object run(List<Object> param) {
        if (param.isEmpty()) {
            return null;
        }
        SettleDecimal n1 = TypeConverter.convert(param.get(0), SettleDecimal.class);
        for (int i = 1; i < param.size(); i++) {
            SettleDecimal n2 = TypeConverter.convert(param.get(i), SettleDecimal.class);
            n1 = n1.min(n2);
        }
        return n1;
    }

    public String fetchSelf() {
        return "min";
    }

}
