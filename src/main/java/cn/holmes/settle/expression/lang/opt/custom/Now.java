package cn.holmes.settle.expression.lang.opt.custom;

import cn.holmes.settle.expression.common.converter.TypeConverter;
import cn.holmes.settle.expression.lang.opt.RunMethod;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 时间函数
 */
public class Now implements RunMethod {

    public Object run(List<Object> fetchParam) {
        if (fetchParam == null || fetchParam.isEmpty()) {
            return System.currentTimeMillis();
        }
        String pattern = TypeConverter.convert(fetchParam.get(0), String.class);
        return new SimpleDateFormat(pattern).format(new Date());
    }

    public String fetchSelf() {
        return "now";
    }
}
