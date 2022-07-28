package com.holmes.settle.expression.lang.obj;


/**
 * 标识符对象,即所有非数字,非字符串的对象.
 *
 */
public class IdentifierObj extends AbstractObj{

    public IdentifierObj(String val) {
        super(val);
    }
}