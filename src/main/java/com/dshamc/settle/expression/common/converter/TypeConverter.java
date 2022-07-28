package com.dshamc.settle.expression.common.converter;

import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;

public class TypeConverter extends DefaultConversionService {

    static {
        DefaultConversionService conversionService = (DefaultConversionService) DefaultConversionService.getSharedInstance();
        conversionService.addConverter(new SettleDecimalConverter());
    }

    public static <T> T warp(Object source, Class<T> targetType) {
        ConversionService conversionService = DefaultConversionService.getSharedInstance();
        return conversionService.convert(source, targetType);
    }

    public static boolean canWarp(Class<?> sourceType, Class<?> targetType) {
        ConversionService conversionService = DefaultConversionService.getSharedInstance();
        return conversionService.canConvert(sourceType, targetType);
    }
}
