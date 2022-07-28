package com.dshamc.settle.expression.el.opt.bit;

import com.dshamc.settle.expression.common.converter.TypeConverter;
import com.dshamc.settle.expression.common.element.SettleDecimal;
import com.dshamc.settle.expression.el.opt.TwoTernary;

/**
 * 与
 */
public class BitAnd extends TwoTernary {

    public int fetchPriority() {
        return 8;
    }

    public Object calculate() {
        SettleDecimal lval = TypeConverter.warp(calculateItem(this.left), SettleDecimal.class);
        SettleDecimal rval = TypeConverter.warp(calculateItem(this.right), SettleDecimal.class);
        return lval.getInner().intValue() & rval.getInner().intValue();
    }

    public String fetchSelf() {
        return "&";
    }

}
