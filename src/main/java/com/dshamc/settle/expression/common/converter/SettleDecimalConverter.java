package com.dshamc.settle.expression.common.converter;

import com.dshamc.settle.expression.common.element.SettleDecimal;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;

import java.util.HashSet;
import java.util.Set;

/**
 * 自定义类型转换器
 */
public class SettleDecimalConverter implements GenericConverter {

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        Set<ConvertiblePair> convertiblePairs = new HashSet<>();
        convertiblePairs.add(new ConvertiblePair(Number.class, SettleDecimal.class));
        convertiblePairs.add(new ConvertiblePair(String.class, SettleDecimal.class));
        return convertiblePairs;
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        if (sourceType.getType() == SettleDecimal.class) {
            return source;
        }
        if (sourceType.getType() == String.class) {
            String number = (String) source;
            return SettleDecimal.warp(number);
        } else {
            Number number = (Number) source;
            return SettleDecimal.warp(number.toString());
        }
    }
}
