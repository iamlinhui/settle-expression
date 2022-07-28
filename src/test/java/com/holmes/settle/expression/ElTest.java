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

        Context context = Lang.context();
        context.set("abc", SettleDecimal.warp("1"));
        System.out.println(El.eval(context, "abc.divide(3,half_up(3))"));

        System.out.println(El.eval("1.divide(3,half_up(9)).multiply(3).scale(half_up(0))"));

        System.out.println(El.eval("1.divide(3,half_up(9)).multiply(3,half_up(5))"));

        System.out.println(El.eval("1.divide(3,half_up(9))"));

        System.out.println(El.eval("1.divide(3)"));

        System.out.println(El.eval("'1.234'.divide(3)"));

        System.out.println(El.eval("1.234.divide(3)"));

        System.out.println(El.eval("1.234 / 3"));

        System.out.println(El.eval("((10000*0.006666666667*(0.006666666667+1).pow(3))/((0.006666666667+1).pow(3)-1)).scale(half_up(2))"));

        LocalDate localDate = LocalDate.of(2017, 2, 15);
        context.set("start", localDate);
        context.set("end", new Date());
        System.out.println(El.eval(context, "days(start,end)"));


        System.out.println(El.eval("1 / 3 * 3"));
        System.out.println(El.eval("(1 / 3 * 3).scale(half_up(2))"));

        Segment segment = Segments.create("${aaa}");
        segment.set("aaa", "bbb");
        CharSequence render = segment.render();
        System.out.println(render);

        System.out.println(El.eval("days('2022-07-01',20220728)+10000"));
    }
}