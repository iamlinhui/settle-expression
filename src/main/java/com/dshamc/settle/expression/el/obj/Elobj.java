package com.dshamc.settle.expression.el.obj;

import com.dshamc.settle.expression.common.context.ElCache;

public interface Elobj {
    String getVal();

    Object fetchVal();

    void setEc(ElCache ec);
}
