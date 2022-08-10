package cn.holmes.settle.expression.lang.opt.custom;

import cn.holmes.settle.expression.common.ElException;
import cn.holmes.settle.expression.common.converter.TypeConverter;
import cn.holmes.settle.expression.lang.opt.RunMethod;

import java.util.List;

/**
 * 去掉字符串两边的空格
 */
public class Trim implements RunMethod {

    public Object run(List<Object> fetchParam) {
        if (fetchParam.isEmpty()) {
            throw new ElException("trim方法参数错误");
        }
        String obj = TypeConverter.convert(fetchParam.get(0), String.class);
        return obj.trim();
    }

    public String fetchSelf() {
        return "trim";
    }
}
