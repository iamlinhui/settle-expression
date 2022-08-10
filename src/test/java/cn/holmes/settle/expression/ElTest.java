package cn.holmes.settle.expression;


import cn.holmes.settle.expression.common.Lang;
import cn.holmes.settle.expression.common.SpringBean;
import cn.holmes.settle.expression.common.context.Context;
import cn.holmes.settle.expression.common.element.SettleDecimal;
import cn.holmes.settle.expression.common.segment.Segment;
import cn.holmes.settle.expression.common.segment.Segments;
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringBean.class)
public class ElTest {


    @Test
    public void method() {
        Context context = Lang.context();
        context.set("A", "12");
        Object eval = El.eval(context, "half_up(A)");
        System.out.println(eval);
    }

    @Test
    public void bean() {
        System.out.println(El.eval("@springBean"));
    }

    @Test
    public void order() {

        // 第一步: 3 / 9 ; 第二步: 1 * 0.33
        System.out.println(El.eval("1 * 3.div(9,half_up(2))"));

        // 第一步: 1 * 3 ; 第二步: 3 / 9
        System.out.println(El.eval("1 * 3 / 9"));
    }

    @Test
    public void contextFor() {
        Context context = Lang.context();
        Demo demo = new Demo("吴彦祖");
        context.putAll(demo);
        System.out.println(El.eval(context, "name")); // 吴彦祖
    }


    @Test
    public void date() throws ParseException {
        Context context = Lang.context();
        context.set("剩余本金", 1000);
        context.set("年利率", 0.08);
        context.set("起息日", new SimpleDateFormat("yyyy-MM-dd").parse("2022-07-02"));
        context.set("结算日", new SimpleDateFormat("yyyy-MM-dd").parse("2022-07-21"));
        BigDecimal eval = El.eval(context, "剩余本金*年利率/360*((结算日-1-起息日))", BigDecimal.class);
        System.out.println(eval.setScale(2, RoundingMode.HALF_UP)); // 4.00

        System.out.println(1000 * 0.08 / 360 * 18);//4.0

        Integer days = El.eval(context, "days(结算日,起息日)", Integer.class);
        System.out.println(days);
    }


    @Test
    public void type() {
        Integer eval = El.eval("1.223", Integer.class);
        System.out.println(eval); // 1
    }

    @Test
    public void now() {
        System.out.println(El.eval("now()"));
        System.out.println(El.eval("now('yyyy-MM-dd')"));
    }

    @Test
    public void object() {

        Demo demo = new Demo("吴彦祖");
        Context context = Lang.context();
        context.set("demo", demo);
        System.out.println(El.eval(context, "demo.name"));
    }

    @Test
    public void array() {
        List<Integer> array = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
        Context context = Lang.context();
        context.set("array", array);
        System.out.println(El.eval(context, "array[5]"));
        System.out.println(El.eval(context, "array.size"));
        System.out.println(El.eval(context, "array.isEmpty()"));
        System.out.println(El.eval(context, "!array.isEmpty()"));
    }

    @Test
    public void map() {
        Map<String, Object> map = new HashMap<>();
        map.put("a", "aaa");
        map.put("b", "bbb");
        map.put("c", "ccc");
        map.put("d", "ddd");
        Context context = Lang.context();
        context.set("map", map);

        System.out.println(El.eval(context, "map[a]")); // aaa
        System.out.println(El.eval(context, "map[1]")); // null
        System.out.println(El.eval(context, "map.d")); //ddd
    }

    @Test
    public void ternary() {

        System.out.println(El.eval("1 > 2 ? 'aaa' : 'bbb'")); //bbb
        System.out.println(El.eval("1 < 2 ? 'ccc' : 'ddd'")); // ccc


        Context context = Lang.context();
        context.set("aaa", 123);
        context.set("bbb", 456);
        context.set("ccc", 789);
        context.set("ddd", 101112);

        System.out.println(El.eval(context, "1 > 2 ? aaa : bbb")); // 456
        System.out.println(El.eval(context, "1 < 2 ? ccc : ddd")); // 789
    }

    @Test
    public void max() {
        System.out.println(El.eval("max(1,2,3,4,5,6,7,99,54)")); // 99
        System.out.println(El.eval("min(-100,2,3,4,5,6,7,99,54)")); // -100
    }

    @Test
    public void segment() {
        Segment segment = Segments.create("${aaa}");
        segment.set("aaa", "bbb");
        CharSequence render = segment.render();
        System.out.println(render); // bbb
    }

    @Test
    public void context() {
        Context context = Lang.context();
        context.set("abc", SettleDecimal.warp("1"));
        System.out.println(El.eval(context, "abc.div(3,half_up(3))")); // 0.333
    }

    @Test
    public void not() {
        System.out.println(El.eval("false"));
        System.out.println(El.eval("!true"));
        System.out.println(El.eval("!false"));
    }

    @Test
    public void days() {
        Context context = Lang.context();
        LocalDate localDate = LocalDate.of(2017, 2, 15);
        context.set("start", localDate);
        context.set("end", new Date());
        System.out.println(El.eval(context, "days(start,end)"));

        System.out.println(El.eval("days('2022-07-01',20220728)")); // 27
    }

    @Test
    public void pmt() {
        String el = "(10000*0.006666666667*(0.006666666667+1).pow(3)).div((0.006666666667+1).pow(3)-1,half_up(2))";
        System.out.println(El.eval(el)); // 3377.88
    }

    @Test
    public void calc() {
        System.out.println(El.eval("1.div(3,half_up(9)).mul(3).off(half_up(0))"));
        System.out.println(El.eval("1.div(3,half_up(9)).mul(3,half_up(5))"));
        System.out.println(El.eval("1.div(3,half_up(9))"));
        System.out.println(El.eval("1.div(3)"));

        System.out.println(El.eval("'1.234'.div(3)"));
        System.out.println(El.eval("1.234.div(3)"));
        System.out.println(El.eval("1.234 / 3"));

        System.out.println(El.eval("1 / 3 * 3"));
        System.out.println(El.eval("(1 / 3 * 3).off(half_up(2))"));
    }


    class Demo {
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