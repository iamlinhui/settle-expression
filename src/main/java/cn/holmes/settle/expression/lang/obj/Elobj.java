package cn.holmes.settle.expression.lang.obj;

import cn.holmes.settle.expression.common.context.ElCache;

public interface Elobj {
    String getVal();

    Object fetchVal();

    void setEc(ElCache ec);
}
