package com.yupi.springbootinit.common;

import java.io.Serializable;
import lombok.Data;

/**
 * 通用返回类
 *
 * @param <T>
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@Data
public class ${className}<T> implements Serializable {

    private int code;

    private T data;

    private String message;

    public ${className}(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public ${className}(int code, T data) {
        this(code, data, "");
    }

    public ${className}(ErrorCode errorCode) {
        this(errorCode.getCode(), null, errorCode.getMessage());
    }
}
