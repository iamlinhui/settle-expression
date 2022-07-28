package com.holmes.settle.expression;


import com.holmes.settle.expression.common.Lang;
import com.holmes.settle.expression.common.context.Context;
import com.holmes.settle.expression.common.element.SettleDecimal;
import com.holmes.settle.expression.common.segment.Segment;
import com.holmes.settle.expression.common.segment.Segments;

import java.time.LocalDate;
import java.util.Date;

public class ElTest {

    public static void main(String[] args) {
        context();
        calc();
        pmt();
        not();
        segment();
        days();
        max();
        question();
    }

    public static void question() {

        System.out.println(El.eval("1 > 2 ? 'aaa' : 'bbb'"));
        System.out.println(El.eval("1 < 2 ? 'ccc' : 'ddd'"));


        Context context = Lang.context();
        context.set("aaa", 123);
        context.set("bbb", 456);
        context.set("ccc", 789);
        context.set("ddd", 101112);

        System.out.println(El.eval(context, "1 > 2 ? aaa : bbb"));
        System.out.println(El.eval(context, "1 < 2 ? ccc : ddd"));
    }


    public static void max() {
        System.out.println(El.eval("max(1,2,3,4,5,6,7,99,54)"));
        System.out.println(El.eval("min(-100,2,3,4,5,6,7,99,54)"));
    }

    public static void segment() {
        Segment segment = Segments.create("${aaa}");
        segment.set("aaa", "bbb");
        CharSequence render = segment.render();
        System.out.println(render);
    }

    public static void context() {
        Context context = Lang.context();
        context.set("abc", SettleDecimal.warp("1"));
        System.out.println(El.eval(context, "abc.div(3,half_up(3))"));
    }

    public static void not() {
        System.out.println(El.eval("false"));
        System.out.println(El.eval("!true"));
        System.out.println(El.eval("!false"));
    }

    public static void days() {
        Context context = Lang.context();
        LocalDate localDate = LocalDate.of(2017, 2, 15);
        context.set("start", localDate);
        context.set("end", new Date());
        System.out.println(El.eval(context, "days(start,end)"));

        System.out.println(El.eval("days('2022-07-01',20220728)"));
    }

    public static void pmt() {
        System.out.println(El.eval("((10000*0.006666666667*(0.006666666667+1).pow(3))/((0.006666666667+1).pow(3)-1)).off(half_up(2))"));
    }

    public static void calc() {
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
}