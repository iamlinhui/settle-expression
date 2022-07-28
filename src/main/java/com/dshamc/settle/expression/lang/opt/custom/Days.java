package com.dshamc.settle.expression.lang.opt.custom;

import com.dshamc.settle.expression.lang.opt.RunMethod;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class Days implements RunMethod {

    @Override
    public Object run(List<Object> param) {
        if (param.size() <= 0) {
            return null;
        }
        Date start = (Date) param.get(0);
        Date end = (Date) param.get(1);
        return betweenDay(start, end);
    }

    /**
     * 两个时间之间相差的天数
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     */
    public static long betweenDay(Date startTime, Date endTime) {
        LocalDate localStartTime = LocalDateTime.ofInstant(startTime.toInstant(), ZoneId.systemDefault()).toLocalDate();
        LocalDate localEndTime = LocalDateTime.ofInstant(endTime.toInstant(), ZoneId.systemDefault()).toLocalDate();
        return localEndTime.toEpochDay() - localStartTime.toEpochDay();
    }

    @Override
    public String fetchSelf() {
        return "days";
    }
}

