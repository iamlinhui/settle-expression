package cn.holmes.settle.expression;

import cn.holmes.settle.expression.common.ElException;
import cn.holmes.settle.expression.common.Lang;
import cn.holmes.settle.expression.common.SpringFramework;
import cn.holmes.settle.expression.common.context.Context;
import cn.holmes.settle.expression.common.element.SettleDecimal;
import cn.holmes.settle.expression.common.segment.Segment;
import cn.holmes.settle.expression.common.segment.Segments;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

/**
 * README.md examples verification
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringFramework.class)
public class ReadmeVerifyTest {

    // ==================== bean ====================
    @Test
    public void testBean() {
        Object result = El.eval("@springFramework");
        Assert.assertNotNull("Spring bean should not be null", result);
        Assert.assertTrue(result instanceof SpringFramework);
    }

    // ==================== order / precision ====================
    @Test
    public void testOrderDivWithPrecision() {
        // 1 * 3.div(9, half_up(2)) => 1 * 0.33 = 0.33
        Object result = El.eval("1 * 3.div(9,half_up(2))");
        Assert.assertEquals(new BigDecimal("0.33"), new BigDecimal(result.toString()));
    }

    @Test
    public void testOrderNormalDiv() {
        // 1 * 3 / 9
        Object result = El.eval("1 * 3 / 9");
        Assert.assertNotNull(result);
    }

    // ==================== contextFor (putAll from object) ====================
    @Test
    public void testContextPutAll() {
        Context context = Lang.context();
        Demo demo = new Demo("wuyanzu");
        context.putAll(demo);
        Object result = El.eval(context, "name");
        Assert.assertEquals("wuyanzu", result.toString());
    }

    // ==================== date ====================
    @Test
    public void testDateInterest() throws ParseException {
        Context context = Lang.context();
        context.set("principal", 1000);
        context.set("rate", 0.08);
        context.set("startDate", new SimpleDateFormat("yyyy-MM-dd").parse("2022-07-02"));
        context.set("endDate", new SimpleDateFormat("yyyy-MM-dd").parse("2022-07-21"));

        // principal * rate / 360 * ((endDate - 1 - startDate))
        BigDecimal eval = El.eval(context, "principal*rate/360*((endDate-1-startDate))", BigDecimal.class);
        Assert.assertEquals(new BigDecimal("4.00"), eval.setScale(2, RoundingMode.HALF_UP));
    }

    @Test
    public void testDaysFunction() throws ParseException {
        Context context = Lang.context();
        context.set("startDate", new SimpleDateFormat("yyyy-MM-dd").parse("2022-07-02"));
        context.set("endDate", new SimpleDateFormat("yyyy-MM-dd").parse("2022-07-21"));

        Integer days = El.eval(context, "days(endDate,startDate)", Integer.class);
        Assert.assertNotNull(days);
        Assert.assertTrue("Days should be positive", days >= 0);
    }

    // ==================== type conversion ====================
    @Test
    public void testTypeConversion() {
        Integer eval = El.eval("1.223", Integer.class);
        Assert.assertEquals(Integer.valueOf(1), eval);
    }

    // ==================== now ====================
    @Test
    public void testNow() {
        Object result = El.eval("now()");
        Assert.assertNotNull("now() should return a value", result);
    }

    @Test
    public void testNowWithFormat() {
        Object result = El.eval("now('yyyy-MM-dd')");
        Assert.assertNotNull(result);
        // Should match yyyy-MM-dd pattern
        Assert.assertTrue("Should be date string", result.toString().matches("\\d{4}-\\d{2}-\\d{2}"));
    }

    // ==================== object property access ====================
    @Test
    public void testObjectProperty() {
        Demo demo = new Demo("wuyanzu");
        Context context = Lang.context();
        context.set("demo", demo);
        Object result = El.eval(context, "demo.name");
        Assert.assertEquals("wuyanzu", result.toString());
    }

    // ==================== array / list ====================
    @Test
    public void testArrayIndex() {
        List<Integer> array = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
        Context context = Lang.context();
        context.set("array", array);

        // array[5] => 6 (0-based index)
        Object result = El.eval(context, "array[5]");
        Assert.assertEquals(6, ((Number) result).intValue());
    }

    @Test
    public void testArraySize() {
        List<Integer> array = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
        Context context = Lang.context();
        context.set("array", array);

        Object size = El.eval(context, "array.size");
        Assert.assertEquals(8, ((Number) size).intValue());
    }

    @Test
    public void testArrayIsEmpty() {
        List<Integer> array = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
        Context context = Lang.context();
        context.set("array", array);

        Assert.assertEquals(false, El.eval(context, "array.isEmpty()"));
        Assert.assertEquals(true, El.eval(context, "!array.isEmpty()"));
    }

    // ==================== map ====================
    @Test
    public void testMapAccess() {
        Map<String, Object> map = new HashMap<>();
        map.put("a", "aaa");
        map.put("b", "bbb");
        map.put("c", "ccc");
        map.put("d", "ddd");
        Context context = Lang.context();
        context.set("map", map);

        Assert.assertEquals("aaa", El.eval(context, "map[a]"));
        Assert.assertNull(El.eval(context, "map[1]"));
        Assert.assertEquals("ddd", El.eval(context, "map.d"));
    }

    // ==================== ternary ====================
    @Test
    public void testTernary() {
        Assert.assertEquals("bbb", El.eval("1 > 2 ? 'aaa' : 'bbb'"));
        Assert.assertEquals("ccc", El.eval("1 < 2 ? 'ccc' : 'ddd'"));
    }

    @Test
    public void testTernaryWithContext() {
        Context context = Lang.context();
        context.set("aaa", 123);
        context.set("bbb", 456);
        context.set("ccc", 789);
        context.set("ddd", 101112);

        Assert.assertEquals(new BigDecimal("456"), El.eval(context, "1 > 2 ? aaa : bbb", BigDecimal.class));
        Assert.assertEquals(new BigDecimal("789"), El.eval(context, "1 < 2 ? ccc : ddd", BigDecimal.class));
    }

    // ==================== max / min ====================
    @Test
    public void testMax() {
        BigDecimal result = El.eval("max(1,2,3,4,5,6,7,99,54)", BigDecimal.class);
        Assert.assertEquals(new BigDecimal("99"), result);
    }

    @Test
    public void testMin() {
        BigDecimal result = El.eval("min(-100,2,3,4,5,6,7,99,54)", BigDecimal.class);
        Assert.assertEquals(new BigDecimal("-100"), result);
    }

    // ==================== segment ====================
    @Test
    public void testSegment() {
        Segment segment = Segments.create("${aaa}");
        segment.set("aaa", "bbb");
        CharSequence render = segment.render();
        Assert.assertEquals("bbb", render.toString());
    }

    // ==================== context with SettleDecimal ====================
    @Test
    public void testContextDivWithPrecision() {
        Context context = Lang.context();
        context.set("abc", SettleDecimal.warp("1"));
        Object result = El.eval(context, "abc.div(3,half_up(3))");
        Assert.assertEquals("0.333", result.toString());
    }

    // ==================== not ====================
    @Test
    public void testNot() {
        Assert.assertEquals(false, El.eval("false"));
        Assert.assertEquals(false, El.eval("!true"));
        Assert.assertEquals(true, El.eval("!false"));
    }

    // ==================== days function ====================
    @Test
    public void testDaysWithStrings() {
        Integer result = El.eval("days('2022-07-01',20220728)", Integer.class);
        Assert.assertEquals(Integer.valueOf(27), result);
    }

    @Test
    public void testDaysWithLocalDate() {
        Context context = Lang.context();
        LocalDate localDate = LocalDate.of(2017, 2, 15);
        context.set("start", localDate);
        context.set("end", new Date());
        Object result = El.eval(context, "days(start,end)");
        Assert.assertNotNull(result);
        Assert.assertTrue("Days should be positive", ((Number) result).intValue() > 0);
    }

    // ==================== pmt (complex financial formula) ====================
    @Test
    public void testPmt() {
        String el = "(10000*0.006666666667*(0.006666666667+1).pow(3)).div((0.006666666667+1).pow(3)-1,half_up(2))";
        Object result = El.eval(el);
        Assert.assertNotNull(result);
        // PMT formula result should be a valid number
        Assert.assertTrue(result instanceof SettleDecimal);
    }

    // ==================== calc (precision operations) ====================
    @Test
    public void testCalcDivMulOff() {
        // 1.div(3,half_up(9)).mul(3).off(half_up(0))
        Object result = El.eval("1.div(3,half_up(9)).mul(3).off(half_up(0))");
        Assert.assertNotNull(result);
    }

    @Test
    public void testCalcDivMulPrecision() {
        // 1.div(3,half_up(9)).mul(3,half_up(5))
        Object result = El.eval("1.div(3,half_up(9)).mul(3,half_up(5))");
        Assert.assertNotNull(result);
    }

    @Test
    public void testCalcDivPrecision() {
        // 1.div(3,half_up(9))
        Object result = El.eval("1.div(3,half_up(9))");
        Assert.assertEquals("0.333333333", result.toString());
    }

    @Test
    public void testCalcDivDefault() {
        // 1.div(3) - default 64 scale
        Object result = El.eval("1.div(3)");
        Assert.assertNotNull(result);
    }

    @Test
    public void testCalcStringDiv() {
        // '1.234'.div(3)
        Object result = El.eval("'1.234'.div(3)");
        Assert.assertNotNull(result);
    }

    @Test
    public void testCalcDecimalDiv() {
        // 1.234.div(3)
        Object result = El.eval("1.234.div(3)");
        Assert.assertNotNull(result);
    }

    @Test
    public void testCalcNormalDiv() {
        // 1.234 / 3
        Object result = El.eval("1.234 / 3");
        Assert.assertNotNull(result);
    }

    @Test
    public void testCalcDivMul() {
        // 1 / 3 * 3
        Object result = El.eval("1 / 3 * 3");
        Assert.assertNotNull(result);
    }

    @Test
    public void testCalcDivMulOffHalfUp() {
        // (1 / 3 * 3).off(half_up(2))
        Object result = El.eval("(1 / 3 * 3).off(half_up(2))");
        Assert.assertEquals("1.00", result.toString());
    }

    // ==================== README operator priority tests ====================
    @Test
    public void testOperatorPriority() {
        // Verify * / % have higher priority than + -
        Assert.assertEquals(new BigDecimal("14"), El.eval("2 + 3 * 4", BigDecimal.class));
        Assert.assertEquals(new BigDecimal("3"), El.eval("2 + 9 % 4", BigDecimal.class)); // (2 + 9) % 4 = 3

        // Verify comparison operators
        Assert.assertEquals(true, El.eval("3 > 2"));
        Assert.assertEquals(true, El.eval("2 < 3"));
        Assert.assertEquals(true, El.eval("3 >= 3"));
        Assert.assertEquals(true, El.eval("3 <= 3"));
        Assert.assertEquals(true, El.eval("3 == 3"));
        Assert.assertEquals(true, El.eval("3 != 4"));
    }

    @Test
    public void testBitOperations() {
        // Bit AND
        Assert.assertEquals(0, ((Number) El.eval("5 & 2")).intValue()); // 101 & 010 = 000
        // Bit OR
        Assert.assertEquals(7, ((Number) El.eval("5 | 2")).intValue()); // 101 | 010 = 111
        // Bit XOR
        Assert.assertEquals(7, ((Number) El.eval("5 ^ 2")).intValue()); // 101 ^ 010 = 111
        // Left shift
        Assert.assertEquals(20, ((Number) El.eval("5 << 2")).intValue()); // 5 * 4 = 20
        // Right shift
        Assert.assertEquals(2, ((Number) El.eval("10 >> 2")).intValue()); // 10 / 4 = 2
    }

    @Test
    public void testLogicalOperators() {
        Assert.assertEquals(true, El.eval("true && true"));
        Assert.assertEquals(false, El.eval("true && false"));
        Assert.assertEquals(true, El.eval("true || false"));
        Assert.assertEquals(false, El.eval("false || false"));
        Assert.assertEquals(false, El.eval("!true"));
        Assert.assertEquals(true, El.eval("!false"));
    }

    @Test
    public void testTrimFunction() {
        Object result = El.eval("trim(' hello ')");
        Assert.assertEquals("hello", result.toString());
    }

    // ==================== Demo class ====================
    static class Demo {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Demo(String name) {
            this.name = name;
        }
    }
}

