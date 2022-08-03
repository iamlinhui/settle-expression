package cn.holmes.settle.expression.lang.parse;

import cn.holmes.settle.expression.common.ElException;
import cn.holmes.settle.expression.lang.opt.arithmetic.*;
import cn.holmes.settle.expression.lang.opt.bit.*;
import cn.holmes.settle.expression.lang.opt.logic.*;
import cn.holmes.settle.expression.lang.opt.object.AccessOpt;
import cn.holmes.settle.expression.lang.opt.object.ArrayOpt;
import cn.holmes.settle.expression.lang.opt.object.CommaOpt;
import cn.holmes.settle.expression.lang.opt.object.FetchArrayOpt;

/**
 * 操作符转换器
 */
public class OptParse implements Parse {

    @Override
    public Object fetchItem(CharQueue exp) {

        switch (exp.peek()) {
            case '+':
                exp.poll();
                return new AddOpt();
            case '-':
                exp.poll();
                return new SubOpt();
            case '*':
                exp.poll();
                return new MulOpt();
            case '/':
                exp.poll();
                return new DivOpt();
            case '%':
                exp.poll();
                return new ModOpt();
            case '(':
                exp.poll();
                return new LBracketOpt();
            case ')':
                exp.poll();
                return new RBracketOpt();
            case '>':
                exp.poll();
                switch (exp.peek()) {
                    case '=':
                        exp.poll();
                        return new GteOpt();
                    case '>':
                        exp.poll();
                        if (exp.peek() == '>') {
                            exp.poll();
                            return new UnsignedLeftShift();
                        }
                        return new RightShift();
                    default:
                }
                return new GtOpt();
            case '<':
                exp.poll();
                switch (exp.peek()) {
                    case '=':
                        exp.poll();
                        return new LteOpt();
                    case '<':
                        exp.poll();
                        return new LeftShift();
                    default:
                }
                return new LtOpt();
            case '=':
                exp.poll();
                switch (exp.peek()) {
                    case '=':
                        exp.poll();
                        return new EqOpt();
                    default:
                }
                throw new ElException("表达式错误,请检查'='后是否有非法字符!");
            case '!':
                exp.poll();
                switch (exp.peek()) {
                    case '=':
                        exp.poll();
                        return new NeqOpt();
                    default:
                }
                return new NotOpt();
            case '|':
                exp.poll();
                switch (exp.peek()) {
                    case '|':
                        exp.poll();
                        return new OrOpt();
                    default:
                }
                return new BitOr();
            case '&':
                exp.poll();
                switch (exp.peek()) {
                    case '&':
                        exp.poll();
                        return new AndOpt();
                    default:
                }
                return new BitAnd();
            case '~':
                exp.poll();
                return new BitNot();
            case '^':
                exp.poll();
                return new BitXor();
            case '?':
                exp.poll();
                return new TernaryOpt();
            case ':':
                exp.poll();
                return new SelectOpt();
            case '.':
                char p = exp.peek(1);
                if (p != '\'' && p != '"' && !Character.isJavaIdentifierStart(p)) {
                    return NULL;
                }
                exp.poll();
                return new AccessOpt();
            case ',':
                exp.poll();
                return new CommaOpt();
            case '[':
                exp.poll();
                return new Object[]{new ArrayOpt(), new LBracketOpt()};
            case ']':
                exp.poll();
                return new Object[]{new RBracketOpt(), new FetchArrayOpt()};
            default:
        }
        return NULL;
    }

}
