package cn.holmes.settle.expression.common.element;


import java.math.RoundingMode;

/**
 * 精度
 */
public class Precision {

    private int scale;
    private RoundingMode roundingMode;

    public Precision(int scale, RoundingMode roundingMode) {
        this.scale = scale;
        this.roundingMode = roundingMode;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public RoundingMode getRoundingMode() {
        return roundingMode;
    }

    public void setRoundingMode(RoundingMode roundingMode) {
        this.roundingMode = roundingMode;
    }

}
