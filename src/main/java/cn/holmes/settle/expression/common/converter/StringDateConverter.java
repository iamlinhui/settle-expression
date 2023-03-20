package cn.holmes.settle.expression.common.converter;

import cn.holmes.settle.expression.common.ElException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

/**
 * 自定义类型转换器
 */
public class StringDateConverter implements Converter<String, Date> {

    private final String[] pattens = new String[]{"yyyy-MM-dd", "yyyyMMdd", "yyyy-MM-dd HH:mm:ss", "yyyyMMddHHmmss"};

    private Date parseDate(String dateString, String patten) {
        if (!StringUtils.hasText(dateString)) {
            return null;
        }
        LocalDate localDate = LocalDate.parse(dateString, DateTimeFormatter.ofPattern(patten));
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    @Override
    public Date convert(String source) {
        for (String patten : pattens) {
            try {
                return parseDate(source, patten);
            } catch (DateTimeParseException ignored) {
            }
        }
        throw new ElException(String.format("date string [%s] warp to date object error", source));
    }
}
