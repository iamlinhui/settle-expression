package com.holmes.settle.expression.lang.opt.arithmetic;


import com.holmes.settle.expression.common.converter.TypeConverter;
import com.holmes.settle.expression.common.element.SettleDecimal;
import com.holmes.settle.expression.lang.opt.TwoTernary;

/**
 * 取模
 */
public class ModOpt extends TwoTernary {

    public int fetchPriority() {
        return 3;
    }

    public Object calculate() {
        SettleDecimal lval = TypeConverter.convert(calculateItem(this.left), SettleDecimal.class);
        SettleDecimal rval = TypeConverter.convert(calculateItem(this.right), SettleDecimal.class);
        return lval.remainder(rval);
    }

    public String fetchSelf() {
        return "%";
    }

}
