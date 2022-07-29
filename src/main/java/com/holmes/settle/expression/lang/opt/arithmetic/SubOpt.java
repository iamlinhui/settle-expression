package com.holmes.settle.expression.lang.opt.arithmetic;

import com.holmes.settle.expression.common.converter.TypeConverter;
import com.holmes.settle.expression.common.element.SettleDecimal;
import com.holmes.settle.expression.lang.opt.TwoTernary;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * "-"
 */
public class SubOpt extends TwoTernary {

    public String fetchSelf() {
        return "-";
    }

    public int fetchPriority() {
        return 4;
    }

    public Object calculate() {
        Object lval = calculateItem(this.left);
        Object rval = calculateItem(this.right);

        // 日期减法 计算间隔天数
        if (lval instanceof Date && rval instanceof Date) {
            LocalDate rTime = LocalDateTime.ofInstant(((Date) rval).toInstant(), ZoneId.systemDefault()).toLocalDate();
            LocalDate lTime = LocalDateTime.ofInstant(((Date) lval).toInstant(), ZoneId.systemDefault()).toLocalDate();
            return lTime.toEpochDay() - rTime.toEpochDay();
        }

        // 日期减法 往前推移时间
        if (lval instanceof Date) {
            LocalDate lTime = LocalDateTime.ofInstant(((Date) lval).toInstant(), ZoneId.systemDefault()).toLocalDate();
            Long rDays = TypeConverter.convert(rval, Long.class);
            return Date.from(lTime.plusDays(-rDays).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        }

        SettleDecimal nlval = TypeConverter.convert(calculateItem(this.left), SettleDecimal.class);
        SettleDecimal nrval = TypeConverter.convert(calculateItem(this.right), SettleDecimal.class);
        return nlval.sub(nrval);
    }

}
