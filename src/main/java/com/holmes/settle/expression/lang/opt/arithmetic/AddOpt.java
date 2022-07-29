package com.holmes.settle.expression.lang.opt.arithmetic;

import com.holmes.settle.expression.common.converter.TypeConverter;
import com.holmes.settle.expression.common.element.SettleDecimal;
import com.holmes.settle.expression.lang.opt.TwoTernary;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * "+"
 */
public class AddOpt extends TwoTernary {

    public int fetchPriority() {
        return 4;
    }

    public String fetchSelf() {
        return "+";
    }

    public Object calculate() {
        Object lval = calculateItem(this.left);
        Object rval = calculateItem(this.right);

        if (lval instanceof String || rval instanceof String) {
            return lval.toString() + rval.toString();
        }

        // 日期加法 往后推移时间
        if (lval instanceof Date) {
            LocalDate lTime = LocalDateTime.ofInstant(((Date) lval).toInstant(), ZoneId.systemDefault()).toLocalDate();
            Long rDays = TypeConverter.convert(rval, Long.class);
            return Date.from(lTime.plusDays(rDays).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        }

        SettleDecimal nlval = TypeConverter.convert(calculateItem(this.left), SettleDecimal.class);
        SettleDecimal nrval = TypeConverter.convert(calculateItem(this.right), SettleDecimal.class);
        return nlval.add(nrval);
    }

}
