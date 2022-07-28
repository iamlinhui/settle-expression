package com.dshamc.settle.expression.lang.parse;

import com.dshamc.settle.expression.common.ElException;
import com.dshamc.settle.expression.lang.opt.arithmetic.*;
import com.dshamc.settle.expression.lang.opt.bit.*;
import com.dshamc.settle.expression.lang.opt.logic.*;
import com.dshamc.settle.expression.lang.opt.object.AccessOpt;
import com.dshamc.settle.expression.lang.opt.object.ArrayOpt;
import com.dshamc.settle.expression.lang.opt.object.CommaOpt;
import com.dshamc.settle.expression.lang.opt.object.FetchArrayOpt;

/**
 * 操作符转换器
 *
 * @author juqkai(juqkai @ gmail.com)
 */
public class OptParse implements Parse {

    @Override
    public Object fetchItem(CharQueue exp) {
        switch (exp.peek()) {
            case '+':
                exp.poll();
                return new PlusOpt();
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
                        return new GTEOpt();
                    case '>':
                        exp.poll();
                        if (exp.peek() == '>') {
                            exp.poll();
                            return new UnsignedLeftShift();
                        }
                        return new RightShift();
                    default:
                }
                return new GTOpt();
            case '<':
                exp.poll();
                switch (exp.peek()) {
                    case '=':
                        exp.poll();
                        return new LTEOpt();
                    case '<':
                        exp.poll();
                        return new LeftShift();
                    default:
                }
                return new LTOpt();
            case '=':
                exp.poll();
                switch (exp.peek()) {
                    case '=':
                        exp.poll();
                        return new EQOpt();
                    default:
                }
                throw new ElException("表达式错误,请检查'='后是否有非法字符!");
            case '!':
                exp.poll();
                switch (exp.peek()) {
                    case '=':
                        exp.poll();
                        return new NEQOpt();
                    case '!':
                        exp.poll();
                        return new NullableOpt();
                    default:
                }
                return new NotOpt();
            case '|':
                exp.poll();
                switch (exp.peek()) {
                    case '|':
                        exp.poll();
                        if (exp.peek() == '|') {
                            exp.poll();
                            return new OrOpt2();
                        }
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
                return new BitXro();
            case '?':
                exp.poll();
                return new QuestionOpt();
            case ':':
                exp.poll();
                return new QuestionSelectOpt();

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
