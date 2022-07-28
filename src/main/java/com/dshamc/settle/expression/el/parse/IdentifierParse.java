package com.dshamc.settle.expression.el.parse;

import com.dshamc.settle.expression.el.obj.AbstractObj;
import com.dshamc.settle.expression.el.obj.IdentifierObj;

/**
 * 标识符转换
 *
 * @author juqkai(juqkai @ gmail.com)
 */
public class IdentifierParse implements Parse {

    @Override
    public Object fetchItem(CharQueue exp) {
        StringBuilder sb = new StringBuilder();
        if (Character.isJavaIdentifierStart(exp.peek())) {
            sb.append(exp.poll());
            while (!exp.isEmpty() && Character.isJavaIdentifierPart(exp.peek())) {
                sb.append(exp.poll());
            }
            if ("null".equals(sb.toString())) {
                return new IdentifierObj(null);
            }
            if ("true".equals(sb.toString())) {
                return Boolean.TRUE;
            }
            if ("false".equals(sb.toString())) {
                return Boolean.FALSE;
            }
            return new AbstractObj(sb.toString());
        }
        return NULL;
    }

}
