package cn.holmes.settle.expression;

import cn.holmes.settle.expression.common.Lang;
import cn.holmes.settle.expression.common.SpringFramework;
import cn.holmes.settle.expression.common.context.Context;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

/**
 * 表达式引擎计算逻辑全面验证
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringFramework.class)
public class ElVerifyTest {

    // ==================== 基本算术 ====================

    @Test
    public void testAddition() {
        Assert.assertEquals(new BigDecimal("3"), El.eval("1 + 2", BigDecimal.class));
        Assert.assertEquals(new BigDecimal("0"), El.eval("1 + -1", BigDecimal.class));
        Assert.assertEquals(new BigDecimal("0.3"), El.eval("0.1 + 0.2", BigDecimal.class));
    }

    @Test
    public void testSubtraction() {
        Assert.assertEquals(new BigDecimal("1"), El.eval("3 - 2", BigDecimal.class));
        Assert.assertEquals(new BigDecimal("-1"), El.eval("2 - 3", BigDecimal.class));
        Assert.assertEquals(new BigDecimal("0"), El.eval("5 - 5", BigDecimal.class));
    }

    @Test
    public void testMultiplication() {
        Assert.assertEquals(new BigDecimal("6"), El.eval("2 * 3", BigDecimal.class));
        Assert.assertEquals(new BigDecimal("0"), El.eval("0 * 100", BigDecimal.class));
        Assert.assertEquals(new BigDecimal("-6"), El.eval("-2 * 3", BigDecimal.class));
    }

    @Test
    public void testDivision() {
        Assert.assertEquals(new BigDecimal("2"), El.eval("6 / 3", BigDecimal.class).setScale(0));
        Assert.assertEquals(new BigDecimal("-2"), El.eval("-6 / 3", BigDecimal.class).setScale(0));
    }

    @Test
    public void testModulo() {
        Assert.assertEquals(new BigDecimal("1"), El.eval("7 % 3", BigDecimal.class));
        Assert.assertEquals(new BigDecimal("0"), El.eval("6 % 3", BigDecimal.class));
    }

    // ==================== 负数处理 ====================

    @Test
    public void testNegativeNumbers() {
        Assert.assertEquals(new BigDecimal("-3"), El.eval("-3", BigDecimal.class));
        Assert.assertEquals(new BigDecimal("-7"), El.eval("-3 + -4", BigDecimal.class));
        Assert.assertEquals(new BigDecimal("-7"), El.eval("-3 - 4", BigDecimal.class));
        Assert.assertEquals(new BigDecimal("1"), El.eval("-3 + 4", BigDecimal.class));
        Assert.assertEquals(new BigDecimal("12"), El.eval("-3 * -4", BigDecimal.class));
        Assert.assertEquals(new BigDecimal("-12"), El.eval("-3 * 4", BigDecimal.class));
    }

    @Test
    public void testNegativeInParentheses() {
        Assert.assertEquals(new BigDecimal("-3"), El.eval("(-3)", BigDecimal.class));
        Assert.assertEquals(new BigDecimal("7"), El.eval("(-3) + 10", BigDecimal.class));
        Assert.assertEquals(new BigDecimal("-30"), El.eval("(-3) * 10", BigDecimal.class));
    }

    @Test
    public void testNegativeAfterOperators() {
        // 这是上一轮修复的核心 bug
        Assert.assertEquals(true, El.eval("-3 > -6"));
        Assert.assertEquals(true, El.eval("-6 < -3"));
        Assert.assertEquals(true, El.eval("-3 >= -3"));
        Assert.assertEquals(true, El.eval("-3 <= -3"));
        Assert.assertEquals(true, El.eval("-3 == -3"));
        Assert.assertEquals(true, El.eval("-3 != -6"));
    }

    // ==================== 运算符优先级 ====================

    @Test
    public void testPrecedenceMulOverAdd() {
        // 2 + 3 * 4 = 14, 不是 20
        Assert.assertEquals(new BigDecimal("14"), El.eval("2 + 3 * 4", BigDecimal.class));
    }

    @Test
    public void testPrecedenceDivOverSub() {
        // 10 - 6 / 3 = 8, 不是 1.33
        Assert.assertEquals(new BigDecimal("8"), El.eval("10 - 6 / 3", BigDecimal.class).setScale(0));
    }

    @Test
    public void testParenthesesOverride() {
        // (2 + 3) * 4 = 20
        Assert.assertEquals(new BigDecimal("20"), El.eval("(2 + 3) * 4", BigDecimal.class));
        // 10 - (6 / 3) = 8
        Assert.assertEquals(new BigDecimal("8"), El.eval("10 - (6 / 3)", BigDecimal.class).setScale(0));
    }

    @Test
    public void testNestedParentheses() {
        // ((2 + 3) * (4 - 1)) = 15
        Assert.assertEquals(new BigDecimal("15"), El.eval("((2 + 3) * (4 - 1))", BigDecimal.class));
    }

    @Test
    public void testLeftToRightSamePrecedence() {
        // 10 - 3 - 2 = 5, 不是 9
        Assert.assertEquals(new BigDecimal("5"), El.eval("10 - 3 - 2", BigDecimal.class));
        // 12 / 3 / 2 = 2, 不是 8
        Assert.assertEquals(new BigDecimal("2"), El.eval("12 / 3 / 2", BigDecimal.class).setScale(0));
    }

    // ==================== 比较运算 ====================

    @Test
    public void testGreaterThan() {
        Assert.assertEquals(true, El.eval("3 > 2"));
        Assert.assertEquals(false, El.eval("2 > 3"));
        Assert.assertEquals(false, El.eval("3 > 3"));
    }

    @Test
    public void testLessThan() {
        Assert.assertEquals(true, El.eval("2 < 3"));
        Assert.assertEquals(false, El.eval("3 < 2"));
        Assert.assertEquals(false, El.eval("3 < 3"));
    }

    @Test
    public void testGreaterThanOrEqual() {
        Assert.assertEquals(true, El.eval("3 >= 2"));
        Assert.assertEquals(true, El.eval("3 >= 3"));
        Assert.assertEquals(false, El.eval("2 >= 3"));
    }

    @Test
    public void testLessThanOrEqual() {
        Assert.assertEquals(true, El.eval("2 <= 3"));
        Assert.assertEquals(true, El.eval("3 <= 3"));
        Assert.assertEquals(false, El.eval("3 <= 2"));
    }

    @Test
    public void testEquals() {
        Assert.assertEquals(true, El.eval("3 == 3"));
        Assert.assertEquals(false, El.eval("3 == 4"));
    }

    @Test
    public void testNotEquals() {
        Assert.assertEquals(true, El.eval("3 != 4"));
        Assert.assertEquals(false, El.eval("3 != 3"));
    }

    // ==================== 逻辑运算 ====================

    @Test
    public void testLogicalAnd() {
        Assert.assertEquals(true, El.eval("true && true"));
        Assert.assertEquals(false, El.eval("true && false"));
        Assert.assertEquals(false, El.eval("false && true"));
        Assert.assertEquals(false, El.eval("false && false"));
    }

    @Test
    public void testLogicalOr() {
        Assert.assertEquals(true, El.eval("true || true"));
        Assert.assertEquals(true, El.eval("true || false"));
        Assert.assertEquals(true, El.eval("false || true"));
        Assert.assertEquals(false, El.eval("false || false"));
    }

    @Test
    public void testLogicalNot() {
        Assert.assertEquals(false, El.eval("!true"));
        Assert.assertEquals(true, El.eval("!false"));
    }

    @Test
    public void testCompoundLogic() {
        // 1 > 0 && 2 > 1 => true
        Assert.assertEquals(true, El.eval("1 > 0 && 2 > 1"));
        // 1 > 2 || 3 > 2 => true
        Assert.assertEquals(true, El.eval("1 > 2 || 3 > 2"));
        // 1 > 2 && 3 > 2 => false
        Assert.assertEquals(false, El.eval("1 > 2 && 3 > 2"));
    }

    // ==================== 三元运算 ====================

    @Test
    public void testTernaryTrue() {
        Assert.assertEquals("yes", El.eval("true ? 'yes' : 'no'"));
    }

    @Test
    public void testTernaryFalse() {
        Assert.assertEquals("no", El.eval("false ? 'yes' : 'no'"));
    }

    @Test
    public void testTernaryWithExpression() {
        Assert.assertEquals("big", El.eval("10 > 5 ? 'big' : 'small'"));
        Assert.assertEquals("small", El.eval("3 > 5 ? 'big' : 'small'"));
    }

    @Test
    public void testTernaryWithNumbers() {
        Assert.assertEquals(new BigDecimal("100"), El.eval("true ? 100 : 200", BigDecimal.class));
        Assert.assertEquals(new BigDecimal("200"), El.eval("false ? 100 : 200", BigDecimal.class));
    }

    @Test
    public void testNestedTernary() {
        // true ? (false ? 1 : 2) : 3 => 2
        Assert.assertEquals(new BigDecimal("2"), El.eval("true ? (false ? 1 : 2) : 3", BigDecimal.class));
    }

    // ==================== 变量上下文 ====================

    @Test
    public void testVariableArithmetic() {
        Context ctx = Lang.context();
        ctx.set("a", 10);
        ctx.set("b", 3);
        Assert.assertEquals(new BigDecimal("13"), El.eval(ctx, "a + b", BigDecimal.class));
        Assert.assertEquals(new BigDecimal("7"), El.eval(ctx, "a - b", BigDecimal.class));
        Assert.assertEquals(new BigDecimal("30"), El.eval(ctx, "a * b", BigDecimal.class));
    }

    @Test
    public void testVariableComparison() {
        Context ctx = Lang.context();
        ctx.set("x", 5);
        ctx.set("y", 10);
        Assert.assertEquals(true, El.eval(ctx, "x < y"));
        Assert.assertEquals(false, El.eval(ctx, "x > y"));
        Assert.assertEquals(true, El.eval(ctx, "x != y"));
    }

    // ==================== 字符串运算 ====================

    @Test
    public void testStringConcatenation() {
        Assert.assertEquals("hello world", El.eval("'hello' + ' ' + 'world'"));
    }

    @Test
    public void testStringEquality() {
        Assert.assertEquals(true, El.eval("'abc' == 'abc'"));
        Assert.assertEquals(false, El.eval("'abc' == 'def'"));
        Assert.assertEquals(true, El.eval("'abc' != 'def'"));
    }

    // ==================== null 处理 ====================

    @Test
    public void testNullComparison() {
        Assert.assertEquals(true, El.eval("null == null"));
        Assert.assertEquals(false, El.eval("null != null"));
    }

    @Test
    public void testNullLogic() {
        Assert.assertEquals(false, El.eval("null && true"));
        Assert.assertEquals(true, El.eval("!null"));
    }

    // ==================== 复杂表达式 ====================

    @Test
    public void testComplexArithmetic() {
        // (1 + 2) * (3 + 4) - 5 = 16
        Assert.assertEquals(new BigDecimal("16"), El.eval("(1 + 2) * (3 + 4) - 5", BigDecimal.class));
    }

    @Test
    public void testComplexNegative() {
        // -(-3) = 3
        Assert.assertEquals(new BigDecimal("3"), El.eval("-(-3)", BigDecimal.class));
        // -(3 + 2) = -5
        Assert.assertEquals(new BigDecimal("-5"), El.eval("-(3 + 2)", BigDecimal.class));
    }

    @Test
    public void testNegativeInTernary() {
        // -1 > -2 ? -3 : -4 => -3 (因为 -1 > -2 为 true)
        Assert.assertEquals(new BigDecimal("-3"), El.eval("-1 > -2 ? -3 : -4", BigDecimal.class));
        // -2 > -1 ? -3 : -4 => -4 (因为 -2 > -1 为 false)
        Assert.assertEquals(new BigDecimal("-4"), El.eval("-2 > -1 ? -3 : -4", BigDecimal.class));
    }

    @Test
    public void testNegativeWithLogicalOperators() {
        // -3 > -6 && -1 < 0 => true && true => true
        Assert.assertEquals(true, El.eval("-3 > -6 && -1 < 0"));
        // -3 < -6 || -1 > -2 => false || true => true
        Assert.assertEquals(true, El.eval("-3 < -6 || -1 > -2"));
    }

    @Test
    public void testPrecompile() {
        El el = new El("-3 > -6");
        Assert.assertEquals(true, el.eval(Lang.context()));

        El el2 = new El("2 + 3 * 4");
        Assert.assertEquals(new BigDecimal("14"), el2.eval(Lang.context(), BigDecimal.class));
    }

    @Test
    public void testMixedPrecedence() {
        // 2 + 3 * 4 > 10 => 14 > 10 => true
        Assert.assertEquals(true, El.eval("2 + 3 * 4 > 10"));
        // 2 * 3 + 4 > 10 => 10 > 10 => false
        Assert.assertEquals(false, El.eval("2 * 3 + 4 > 10"));
        // 2 * 3 + 4 >= 10 => 10 >= 10 => true
        Assert.assertEquals(true, El.eval("2 * 3 + 4 >= 10"));
    }
}

