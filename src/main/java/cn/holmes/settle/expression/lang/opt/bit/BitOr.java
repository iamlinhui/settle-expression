package cn.holmes.settle.expression.lang.opt.bit;

import cn.holmes.settle.expression.common.converter.TypeConverter;
import cn.holmes.settle.expression.lang.opt.TwoTernary;

/**
 * 或
 */
public class BitOr extends TwoTernary {
    public int fetchPriority() {
        return 10;
    }

    public Object calculate() {
        Integer lval = TypeConverter.convert(calculateItem(this.left), Integer.class);
        Integer rval = TypeConverter.convert(calculateItem(this.right), Integer.class);
        return lval | rval;
    }

    public String fetchSelf() {
        return "|";
    }
}
