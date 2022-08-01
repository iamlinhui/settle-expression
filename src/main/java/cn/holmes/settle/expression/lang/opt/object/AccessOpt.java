package cn.holmes.settle.expression.lang.opt.object;

import cn.holmes.settle.expression.common.context.Context;
import cn.holmes.settle.expression.lang.obj.Elobj;
import cn.holmes.settle.expression.common.ElException;
import cn.holmes.settle.expression.lang.opt.Operator;
import cn.holmes.settle.expression.common.Mirror;
import cn.holmes.settle.expression.lang.opt.RunMethod;
import cn.holmes.settle.expression.lang.opt.TwoTernary;

import java.util.List;
import java.util.Map;

/**
 * 访问符:'.'
 */
public class AccessOpt extends TwoTernary implements RunMethod {

    public int fetchPriority() {
        return 1;
    }

    public Object calculate() {

        Object obj = fetchVar();
        if (obj == null) {
            throw new ElException("obj is NULL, can't call obj." + right);
        }
        if (obj instanceof Map) {
            Map<?, ?> om = (Map<?, ?>) obj;
            if (om.containsKey(right.toString())) {
                return om.get(right.toString());
            }
        }
        if (obj instanceof Context) {
            Context sc = (Context) obj;
            if (sc.has(right.toString())) {
                return sc.get(right.toString());
            }
        }

        Mirror<?> me = Mirror.me(obj);
        return me.getValue(obj, right.toString());
    }

    public Object run(List<Object> param) {
        Object obj = fetchVar();
        Mirror<?> me;
        if (obj == null) {
            throw new NullPointerException();
        }
        if (obj instanceof Class) {
            //也许是个静态方法
            me = Mirror.me(obj);
            try {
                return me.invoke(obj, right.toString(), param.toArray());
            } catch (IllegalStateException e) {
                me = Mirror.me(obj.getClass());
                return me.invoke(obj, right.toString(), param.toArray());
            }
        } else {
            me = Mirror.me(obj);
            return me.invoke(obj, right.toString(), param.toArray());
        }
    }

    /**
     * 取得变得的值
     */
    public Object fetchVar() {
        if (left instanceof Operator) {
            return ((Operator) left).calculate();
        }
        if (left instanceof Elobj) {
            return ((Elobj) left).fetchVal();
        }
        return left;
    }

    public String fetchSelf() {
        return ".";
    }
}
