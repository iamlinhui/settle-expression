package com.dshamc.settle.expression.lang.opt.custom;

import com.dshamc.settle.expression.lang.opt.RunMethod;

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
        return new SimpleDateFormat(fetchParam.get(0).toString()).format(new Date());
    }

    public String fetchSelf() {
        return "now";
    }
}
