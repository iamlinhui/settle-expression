package cn.holmes.settle.expression.lang.parse;

import cn.holmes.settle.expression.common.ElException;
import cn.holmes.settle.expression.common.Lang;
import cn.holmes.settle.expression.lang.obj.Elobj;
import cn.holmes.settle.expression.lang.obj.FieldObj;
import cn.holmes.settle.expression.lang.obj.IdentifierObj;
import cn.holmes.settle.expression.lang.obj.MethodObj;
import cn.holmes.settle.expression.lang.opt.arithmetic.LBracketOpt;
import cn.holmes.settle.expression.lang.opt.arithmetic.NegativeOpt;
import cn.holmes.settle.expression.lang.opt.arithmetic.RBracketOpt;
import cn.holmes.settle.expression.lang.opt.arithmetic.SubOpt;
import cn.holmes.settle.expression.lang.opt.object.AccessOpt;
import cn.holmes.settle.expression.lang.opt.object.CommaOpt;
import cn.holmes.settle.expression.lang.opt.object.InvokeMethodOpt;
import cn.holmes.settle.expression.lang.opt.object.MethodOpt;

import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 转换器,也就是用来将字符串转换成队列.
 */
public class Converter {

    private final List<Parse> parses = new ArrayList<>();

    /**
     * 表达式字符队列
     */
    private final CharQueue exp;
    /**
     * 表达式项
     */
    private LinkedList<Object> itemCache;

    /**
     * 方法栈
     */
    private final LinkedList<MethodOpt> methods = new LinkedList<>();

    /**
     * 上一个数据
     */
    private Object prev = null;

    public Converter(CharQueue reader) {
        this.exp = reader;
        itemCache = new LinkedList<>();
        skipSpace();
        initParse();
    }

    public Converter(String val) {
        this(Lang.inr(val));
    }

    public Converter(Reader reader) {
        this(new CharQueueDefault(reader));
    }

    /**
     * 初始化解析器
     */
    private void initParse() {
        parses.add(new OptParse());
        parses.add(new StringParse());
        parses.add(new IdentifierParse());
        parses.add(new ValParse());
    }

    /**
     * 重新设置解析器
     */
    public void setParse(List<Parse> val) {
        parses.addAll(val);
    }

    /**
     * 初始化EL项
     */
    public void initItems() {
        while (!exp.isEmpty()) {
            Object obj = parseItem();
            // 处理数组的情况
            if (obj.getClass().isArray()) {
                Collections.addAll(itemCache, (Object[]) obj);
                continue;
            }
            itemCache.add(obj);
        }
        itemCache = clearUp(itemCache);
    }

    /**
     * 清理转换后的结果, 主要做一些标识性的转换
     *
     * @param rpn
     * @return
     */
    private LinkedList<Object> clearUp(LinkedList<Object> rpn) {
        LinkedList<Object> dest = new LinkedList<>();
        while (!rpn.isEmpty()) {
            if (!(rpn.getFirst() instanceof Elobj)) {
                dest.add(rpn.removeFirst());
                continue;
            }
            Elobj obj = (Elobj) rpn.removeFirst();
            // 方法对象
            if (!rpn.isEmpty() && rpn.getFirst() instanceof MethodOpt) {
                dest.add(new MethodObj(obj.getVal()));
                continue;
            }
            // 属性对象
            if (!dest.isEmpty()
                    && dest.getLast() instanceof AccessOpt
                    && !rpn.isEmpty()
                    && rpn.getFirst() instanceof AccessOpt) {
                dest.add(new FieldObj(obj.getVal()));
                continue;
            }
            dest.add(new IdentifierObj(obj.getVal()));
        }
        return dest;
    }

    /**
     * 解析数据
     */
    private Object parseItem() {
        Object obj;
        for (Parse parse : parses) {
            obj = parse.fetchItem(exp);
            if (obj != Parse.NULL) {
                skipSpace();
                return parseItem(obj);
            }
        }
        throw new ElException("无法解析!");
    }

    /**
     * 转换数据,主要是转换负号,方法执行
     */
    private Object parseItem(Object item) {
        // 处理参数个数
        if (methods.peek() != null) {
            MethodOpt opt = methods.peek();
            if (opt.getSize() <= 0) {
                if (!(item instanceof CommaOpt) && !(item instanceof RBracketOpt)) {
                    opt.setSize(1);
                }
            } else {
                if (item instanceof CommaOpt) {
                    opt.setSize(opt.getSize() + 1);
                }
            }
        }

        // 左括号
        if (item instanceof LBracketOpt) {
            if (prev instanceof Elobj) {
                MethodOpt prem = new MethodOpt();
                item = new Object[]{prem, new LBracketOpt()};
                methods.addFirst(prem);
            } else {
                methods.addFirst(null);
            }
        }

        // 右括号
        if (item instanceof RBracketOpt) {
            if (methods.poll() != null) {
                item = new Object[]{new RBracketOpt(), new InvokeMethodOpt()};
            }
        }
        // 转换负号'-'
        if (item instanceof SubOpt && NegativeOpt.isNegetive(prev)) {
            item = new NegativeOpt();
        }
        prev = item;
        return item;
    }

    /**
     * 跳过空格,并返回是否跳过空格(是否存在空格)
     */
    private boolean skipSpace() {
        boolean space = false;
        while (!exp.isEmpty() && Character.isWhitespace(exp.peek())) {
            space = true;
            exp.poll();
        }
        return space;
    }

    /**
     * 取得一个有效数据
     */
    public Object fetchItem() {
        return itemCache.poll();
    }

    /**
     * 是否结束
     */
    public boolean isEnd() {
        return itemCache.isEmpty();
    }
}
