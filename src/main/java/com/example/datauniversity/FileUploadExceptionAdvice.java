package com.example.datauniversity;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@ControllerAdvice
public class FileUploadExceptionAdvice {
    @SuppressWarnings("rewtypes")
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity handleMaxSizeException(MaxUploadSizeExceededException exc) {
        System.out.println(exc.getMessage());
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage("file too large!", ""));
    }
}
