package cn.holmes.settle.expression.lang.opt.arithmetic;


import cn.holmes.settle.expression.common.converter.TypeConverter;
import cn.holmes.settle.expression.common.element.SettleDecimal;
import cn.holmes.settle.expression.lang.opt.TwoTernary;

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
