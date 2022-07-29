package com.holmes.settle.expression.lang.opt.custom;

import com.holmes.settle.expression.common.ElException;
import com.holmes.settle.expression.common.element.SettleDecimal;
import com.holmes.settle.expression.lang.opt.RunMethod;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;

public class Days implements RunMethod {

    private final String[] pattens = new String[]{"yyyy-MM-dd", "yyyyMMdd", "yyyy-MM-dd HH:mm:ss", "yyyyMMddHHmmss"};

    @Override
    public Object run(List<Object> param) {
        if (param.isEmpty()) {
            return null;
        }
        Date start = warp(param.get(0));
        Date end = warp(param.get(1));
        return SettleDecimal.warp(String.valueOf(Math.max(betweenDay(start, end), 0L)));
    }

    private Date warp(Object obj) {
        if (obj instanceof Date) {
            return (Date) obj;
        }
        String objStr = obj.toString();
        for (String patten : pattens) {
            try {
                return parseDate(objStr, patten);
            } catch (DateTimeParseException ignored) {
            }
        }
        throw new ElException(String.format("date string [%s] warp to date object error", objStr));
    }

    private Date parseDate(String dateString, String patten) {
        if (!StringUtils.hasText(dateString)) {
            return null;
        }
        LocalDate localDate = LocalDate.parse(dateString, DateTimeFormatter.ofPattern(patten));
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 两个时间之间相差的天数
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     */
    private long betweenDay(Date startTime, Date endTime) {
        LocalDate localStartTime = LocalDateTime.ofInstant(startTime.toInstant(), ZoneId.systemDefault()).toLocalDate();
        LocalDate localEndTime = LocalDateTime.ofInstant(endTime.toInstant(), ZoneId.systemDefault()).toLocalDate();
        return localEndTime.toEpochDay() - localStartTime.toEpochDay();
    }

    @Override
    public String fetchSelf() {
        return "days";
    }
}

