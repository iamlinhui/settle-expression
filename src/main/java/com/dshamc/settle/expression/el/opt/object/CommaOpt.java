package com.dshamc.settle.expression.el.opt.object;

import com.dshamc.settle.expression.el.opt.TwoTernary;

import java.util.ArrayList;
import java.util.List;

/**
 * ","
 * 逗号操作符,将左右两边的数据组织成一个数据
 *
 */
public class CommaOpt extends TwoTernary {
    public int fetchPriority() {
        return 99;
    }

    @SuppressWarnings("unchecked")
    public Object calculate() {
        List<Object> objs = new ArrayList<Object>();
        if(left instanceof CommaOpt){
            List<Object> tem = (List<Object>) ((CommaOpt) left).calculate();
            for(Object t : tem){
                objs.add(t);
            }
        }else{
            objs.add(calculateItem(left));
        }
        objs.add(calculateItem(right));
        return objs;
    }
    public String fetchSelf() {
        return ",";
    }

}
