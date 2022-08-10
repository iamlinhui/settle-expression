package cn.holmes.settle.expression.lang.opt.object;

import cn.holmes.settle.expression.lang.opt.TwoTernary;

import java.util.ArrayList;
import java.util.List;

/**
 * ","
 * 逗号操作符,将左右两边的数据组织成一个数据
 */
public class CommaOpt extends TwoTernary {
    public int fetchPriority() {
        return 99;
    }

    @SuppressWarnings("unchecked")
    public Object calculate() {
        List<Object> objs = new ArrayList<>();
        if (left instanceof CommaOpt) {
            objs.addAll((List<Object>) ((CommaOpt) left).calculate());
        } else {
            objs.add(calculateItem(left));
        }
        objs.add(calculateItem(right));
        return objs;
    }

    public String fetchSelf() {
        return ",";
    }

}
