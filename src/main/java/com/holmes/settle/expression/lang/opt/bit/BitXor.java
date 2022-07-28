package com.holmes.settle.expression.lang.opt.bit;

import com.holmes.settle.expression.common.converter.TypeConverter;
import com.holmes.settle.expression.common.element.SettleDecimal;
import com.holmes.settle.expression.lang.opt.TwoTernary;

/**
 * 异或
 */
public class BitXor extends TwoTernary {
    public int fetchPriority() {
        return 9;
    }

    public Object calculate() {
        SettleDecimal lval = TypeConverter.warp(calculateItem(this.left), SettleDecimal.class);
        SettleDecimal rval = TypeConverter.warp(calculateItem(this.right), SettleDecimal.class);
        return lval.getInner().intValue() ^ rval.getInner().intValue();
    }

    public String fetchSelf() {
        return "^";
    }
}
