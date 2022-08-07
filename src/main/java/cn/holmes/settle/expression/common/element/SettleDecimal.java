package cn.holmes.settle.expression.common.element;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * 数值对象
 */
public class SettleDecimal extends Number implements Comparable<SettleDecimal> {

    private final BigDecimal number;

    public static SettleDecimal warp(String value) {
        return new SettleDecimal(value);
    }

    public BigDecimal bigDecimalValue() {
        return number;
    }

    public SettleDecimal(String val) {
        this.number = new BigDecimal(val);
    }

    public SettleDecimal(BigDecimal val) {
        this.number = val;
    }

    /**
     * 除
     */
    public SettleDecimal div(SettleDecimal divisor) {
        return new SettleDecimal(number.divide(divisor.number, 64, RoundingMode.DOWN));
    }

    public SettleDecimal div(SettleDecimal divisor, Precision precision) {
        return new SettleDecimal(number.divide(divisor.number, precision.getScale(), precision.getRoundingMode()));
    }

    /**
     * 乘
     */
    public SettleDecimal mul(SettleDecimal multiplicand, Precision precision) {
        return new SettleDecimal(number.multiply(multiplicand.number).setScale(precision.getScale(), precision.getRoundingMode()));
    }

    public SettleDecimal mul(SettleDecimal multiplicand) {
        return new SettleDecimal(number.multiply(multiplicand.number));
    }

    /**
     * 减
     */
    public SettleDecimal sub(SettleDecimal subtrahend) {
        return new SettleDecimal(number.subtract(subtrahend.number));
    }

    public SettleDecimal sub(SettleDecimal subtrahend, Precision precision) {
        return new SettleDecimal(number.subtract(subtrahend.number).setScale(precision.getScale(), precision.getRoundingMode()));
    }

    /**
     * 加
     */
    public SettleDecimal add(SettleDecimal augend) {
        return new SettleDecimal(number.add(augend.number));
    }

    public SettleDecimal add(SettleDecimal augend, Precision precision) {
        return new SettleDecimal(number.add(augend.number).setScale(precision.getScale(), precision.getRoundingMode()));
    }

    /**
     * 幂
     */
    public SettleDecimal pow(SettleDecimal index, Precision precision) {
        return new SettleDecimal(number.pow(index.number.intValue()).setScale(precision.getScale(), precision.getRoundingMode()));
    }

    public SettleDecimal pow(SettleDecimal index) {
        return new SettleDecimal(number.pow(index.number.intValue()));
    }

    /**
     * 最大
     */
    public SettleDecimal max(SettleDecimal other) {
        if (number.compareTo(other.number) >= 0) {
            return this;
        } else {
            return other;
        }
    }

    /**
     * 最小
     */
    public SettleDecimal min(SettleDecimal other) {
        if (number.compareTo(other.number) <= 0) {
            return this;
        } else {
            return other;
        }
    }

    /**
     * 取舍
     */
    public SettleDecimal off(Precision precision) {
        return new SettleDecimal(number.setScale(precision.getScale(), precision.getRoundingMode()));
    }

    /**
     * 取模
     */
    public SettleDecimal remainder(SettleDecimal divisor) {
        return new SettleDecimal(number.remainder(divisor.number));
    }

    /**
     * 相反数
     */
    public SettleDecimal negate() {
        return new SettleDecimal(number.negate());
    }

    @Override
    public String toString() {
        return String.valueOf(number);
    }

    @Override
    public int compareTo(SettleDecimal o) {
        return number.compareTo(o.number);
    }

    @Override
    public int intValue() {
        return number.intValue();
    }

    @Override
    public long longValue() {
        return number.longValue();
    }

    @Override
    public float floatValue() {
        return number.floatValue();
    }

    @Override
    public double doubleValue() {
        return number.doubleValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SettleDecimal that = (SettleDecimal) o;
        return Objects.equals(number, that.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }
}
