package com.holmes.settle.expression.common.converter;

import com.holmes.settle.expression.common.element.SettleDecimal;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;

import java.math.BigDecimal;

public class TypeConverter {

    static {
        DefaultConversionService conversionService = (DefaultConversionService) DefaultConversionService.getSharedInstance();
        conversionService.addConverter(new SettleDecimalConverter());
        conversionService.addConverter(SettleDecimal.class, Integer.class, source -> source.getInner().intValue());
        conversionService.addConverter(SettleDecimal.class, Double.class, source -> source.getInner().doubleValue());
        conversionService.addConverter(SettleDecimal.class, Float.class, source -> source.getInner().floatValue());
        conversionService.addConverter(SettleDecimal.class, Byte.class, source -> source.getInner().byteValue());
        conversionService.addConverter(SettleDecimal.class, Long.class, source -> source.getInner().longValue());
        conversionService.addConverter(SettleDecimal.class, BigDecimal.class, SettleDecimal::getInner);
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
