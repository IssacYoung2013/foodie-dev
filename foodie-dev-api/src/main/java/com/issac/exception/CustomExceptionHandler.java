package com.issac.exception;

import com.issac.util.JSONResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * @author: ywy
 * @date: 2020-06-05
 * @desc:
 */
@RestControllerAdvice
public class CustomExceptionHandler {

    /**
     * 上传文件超过500k
     * @param ex
     * @return
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public JSONResult handlerMaxUploadFile(MaxUploadSizeExceededException ex) {
        return JSONResult.errorMsg("文件上传大小不能超过500k");
    }
}
