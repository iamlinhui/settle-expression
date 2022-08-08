package cn.holmes.settle.expression.lang.parse;

import cn.holmes.settle.expression.common.SpringApplication;
import cn.holmes.settle.expression.lang.obj.AbstractObj;
import cn.holmes.settle.expression.lang.obj.IdentifierObj;

/**
 * 标识符转换
 */
public class IdentifierParse implements Parse {

    @Override
    public Object fetchItem(CharQueue exp) {
        StringBuilder sb = new StringBuilder();
        if (Character.isJavaIdentifierStart(exp.peek()) || '@' == exp.peek()) {
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
            if (sb.toString().startsWith("@")) {
                return SpringApplication.getBean(sb.substring(1));
            }
            return new AbstractObj(sb.toString());
        }
        return NULL;
    }

}
