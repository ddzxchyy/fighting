package cn.jzq.generalold.module.framework.springboot.exception;

import cn.jzq.fightingcommon.uitls.R;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 *
 * @author jzq
 * @date 2020-10-12
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 拦截所有异常, 这里只是为了演示，一般情况下一个方法特定处理一种异常
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R exceptionHandler(Exception e) {
        return R.error(e.getLocalizedMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R handleAccessDeniedException(IllegalArgumentException e) {
        return R.error("参数异常：" + e.getLocalizedMessage());
    }

}
