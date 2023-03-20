package cn.holmes.settle.expression.lang.opt.logic;

import cn.holmes.settle.expression.common.converter.TypeConverter;
import cn.holmes.settle.expression.lang.opt.TwoTernary;

import java.math.BigDecimal;
import java.util.Date;

public abstract class AbstractCompareOpt extends TwoTernary {

    @SuppressWarnings({"unchecked", "rawtypes"})
    protected int compare() {

        Object lval = calculateItem(this.left);
        Object rval = calculateItem(this.right);

        // null == null or equal
        if (lval == rval) {
            return 0;
        }
        if (lval != null && rval == null) {
            return 1;
        }
        if (lval == null) {
            return -1;
        }
        if (lval instanceof Number && rval instanceof Number) {
            if (!(lval instanceof BigDecimal)) {
                lval = new BigDecimal(lval.toString());
            }
            if (!(rval instanceof BigDecimal)) {
                rval = new BigDecimal(rval.toString());
            }
            return ((BigDecimal) lval).compareTo((BigDecimal) rval);
        }
        if (lval instanceof Comparable && lval.getClass().isInstance(rval)) {
            return ((Comparable) lval).compareTo(rval);
        }
        // 日期和字符串比较
        if (lval instanceof Date && rval instanceof String) {
            return ((Date) lval).compareTo(TypeConverter.convert(rval, Date.class));
        }
        if (lval instanceof String && rval instanceof Date) {
            return TypeConverter.convert(lval, Date.class).compareTo((Date) rval);
        }
        if (lval.equals(rval)) {
            return 0;
        }
        return lval.toString().compareTo(rval.toString());
    }
}
