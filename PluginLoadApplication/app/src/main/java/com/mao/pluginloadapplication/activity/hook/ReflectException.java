package com.mao.pluginloadapplication.activity.hook;

/**
 * @author Lody
 * 异常处理
 */
class ReflectException extends RuntimeException{
    public ReflectException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReflectException(Throwable cause) {
        super(cause);
    }
}
