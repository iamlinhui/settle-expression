package com.holmes.settle.expression.lang.opt.arithmetic;

import com.holmes.settle.expression.common.converter.TypeConverter;
import com.holmes.settle.expression.common.element.SettleDecimal;
import com.holmes.settle.expression.lang.opt.TwoTernary;

/**
 * 乘
 */
public class MulOpt extends TwoTernary {

    public int fetchPriority() {
        return 3;
    }

    public Object calculate() {
        SettleDecimal lval = TypeConverter.warp(calculateItem(this.left), SettleDecimal.class);
        SettleDecimal rval = TypeConverter.warp(calculateItem(this.right), SettleDecimal.class);
        return lval.mul(rval);
    }

    public String fetchSelf() {
        return "*";
    }

}
