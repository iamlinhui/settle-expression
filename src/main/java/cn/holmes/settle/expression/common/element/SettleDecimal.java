package cn.holmes.settle.expression.common.element;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * 数值对象
 */
public class SettleDecimal extends Number implements Comparable<SettleDecimal> {

    private final BigDecimal inner;

    public static SettleDecimal warp(String value) {
        return new SettleDecimal(value);
    }

    public BigDecimal bigDecimalValue() {
        return inner;
    }

    public SettleDecimal(String val) {
        this.inner = new BigDecimal(val);
    }

    public SettleDecimal(BigDecimal val) {
        this.inner = val;
    }

    /**
     * 除
     */
    public SettleDecimal div(SettleDecimal divisor) {
        return new SettleDecimal(inner.divide(divisor.inner, 64, RoundingMode.DOWN));
    }

    public SettleDecimal div(SettleDecimal divisor, Precision precision) {
        return new SettleDecimal(inner.divide(divisor.inner, precision.getScale(), precision.getRoundingMode()));
    }

    /**
     * 乘
     */
    public SettleDecimal mul(SettleDecimal multiplicand, Precision precision) {
        return new SettleDecimal(inner.multiply(multiplicand.inner).setScale(precision.getScale(), precision.getRoundingMode()));
    }

    public SettleDecimal mul(SettleDecimal multiplicand) {
        return new SettleDecimal(inner.multiply(multiplicand.inner));
    }

    /**
     * 减
     */
    public SettleDecimal sub(SettleDecimal subtrahend) {
        return new SettleDecimal(inner.subtract(subtrahend.inner));
    }

    public SettleDecimal sub(SettleDecimal subtrahend, Precision precision) {
        return new SettleDecimal(inner.subtract(subtrahend.inner).setScale(precision.getScale(), precision.getRoundingMode()));
    }

    /**
     * 加
     */
    public SettleDecimal add(SettleDecimal augend) {
        return new SettleDecimal(inner.add(augend.inner));
    }

    public SettleDecimal add(SettleDecimal augend, Precision precision) {
        return new SettleDecimal(inner.add(augend.inner).setScale(precision.getScale(), precision.getRoundingMode()));
    }

    /**
     * 幂
     */
    public SettleDecimal pow(SettleDecimal index, Precision precision) {
        return new SettleDecimal(inner.pow(index.inner.intValue()).setScale(precision.getScale(), precision.getRoundingMode()));
    }

    public SettleDecimal pow(SettleDecimal index) {
        return new SettleDecimal(inner.pow(index.inner.intValue()));
    }

    /**
     * 最大
     */
    public SettleDecimal max(SettleDecimal other) {
        if (inner.compareTo(other.inner) >= 0) {
            return this;
        } else {
            return other;
        }
    }

    /**
     * 最小
     */
    public SettleDecimal min(SettleDecimal other) {
        if (inner.compareTo(other.inner) <= 0) {
            return this;
        } else {
            return other;
        }
    }

    /**
     * 取舍
     */
    public SettleDecimal off(Precision precision) {
        return new SettleDecimal(inner.setScale(precision.getScale(), precision.getRoundingMode()));
    }

    /**
     * 取模
     */
    public SettleDecimal remainder(SettleDecimal divisor) {
        return new SettleDecimal(inner.remainder(divisor.inner));
    }

    /**
     * 相反数
     */
    public SettleDecimal negate() {
        return new SettleDecimal(inner.negate());
    }

    @Override
    public String toString() {
        return String.valueOf(inner);
    }

    @Override
    public int compareTo(SettleDecimal o) {
        return inner.compareTo(o.inner);
    }

    @Override
    public int intValue() {
        return inner.intValue();
    }

    @Override
    public long longValue() {
        return inner.longValue();
    }

    @Override
    public float floatValue() {
        return inner.floatValue();
    }

    @Override
    public double doubleValue() {
        return inner.doubleValue();
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
        return Objects.equals(inner, that.inner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inner);
    }
}
