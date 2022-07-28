package com.dshamc.settle.expression.common;

public class ElException extends RuntimeException {

    private static final long serialVersionUID = -5172074713426804980L;

    public ElException(String message, Throwable cause) {
        super(message, cause);
    }

    public ElException(String format, Object... args) {
        super(String.format(format, args));
    }

    public ElException(String message) {
        super(message);
    }

    public ElException(Throwable cause) {
        super(cause);
    }

}
