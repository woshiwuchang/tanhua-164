package com.itheima.app.exception;

import com.itheima.domain.vo.ErrorResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice  // 全局异常处理器
public class GlobalExceptionAdvice {


    // 捕获exception异常
    @ExceptionHandler
    public ResponseEntity exceptionHandlerMethod(Exception ex){
        // 将异常信息输出到控制台
        ex.printStackTrace();
        // 友情提示
        return ResponseEntity.status(500).body(ErrorResult.error());
    }
}
