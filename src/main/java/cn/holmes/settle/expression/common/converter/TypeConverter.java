package cn.holmes.settle.expression.common.converter;

import cn.holmes.settle.expression.common.element.SettleDecimal;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;

import java.math.BigDecimal;

public class TypeConverter {

    static {
        DefaultConversionService conversionService = (DefaultConversionService) DefaultConversionService.getSharedInstance();
        conversionService.addConverter(new SettleDecimalConverter());
        conversionService.addConverter(SettleDecimal.class, Integer.class, SettleDecimal::intValue);
        conversionService.addConverter(SettleDecimal.class, Double.class, SettleDecimal::doubleValue);
        conversionService.addConverter(SettleDecimal.class, Float.class, SettleDecimal::floatValue);
        conversionService.addConverter(SettleDecimal.class, Byte.class, SettleDecimal::byteValue);
        conversionService.addConverter(SettleDecimal.class, Long.class, SettleDecimal::longValue);
        conversionService.addConverter(SettleDecimal.class, Short.class, SettleDecimal::shortValue);
        conversionService.addConverter(SettleDecimal.class, BigDecimal.class, SettleDecimal::bigDecimalValue);
        conversionService.addConverter(SettleDecimal.class, String.class, SettleDecimal::toString);
    }

    public static <T> T convert(Object source, Class<T> targetType) {
        ConversionService conversionService = DefaultConversionService.getSharedInstance();
        return conversionService.convert(source, targetType);
    }

    public static boolean canConvert(Class<?> sourceType, Class<?> targetType) {
        ConversionService conversionService = DefaultConversionService.getSharedInstance();
        return conversionService.canConvert(sourceType, targetType);
    }
}
