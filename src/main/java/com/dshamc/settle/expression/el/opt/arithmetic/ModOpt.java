package com.dshamc.settle.expression.el.opt.arithmetic;


import com.dshamc.settle.expression.common.converter.TypeConverter;
import com.dshamc.settle.expression.common.element.SettleDecimal;
import com.dshamc.settle.expression.el.opt.TwoTernary;

/**
 * 取模
 */
public class ModOpt extends TwoTernary {

    public int fetchPriority() {
        return 3;
    }

    public Object calculate() {
        SettleDecimal lval = TypeConverter.warp(calculateItem(this.left), SettleDecimal.class);
        SettleDecimal rval = TypeConverter.warp(calculateItem(this.right), SettleDecimal.class);
        return lval.remainder(rval);
    }

    public String fetchSelf() {
        return "%";
    }

}
