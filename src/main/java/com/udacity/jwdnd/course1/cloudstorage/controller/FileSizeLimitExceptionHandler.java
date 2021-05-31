package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@ControllerAdvice
public class FileSizeLimitExceptionHandler {
    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
    public String exception(MaxUploadSizeExceededException exception, Model model) {
        model.addAttribute("isSuccess", false);
        model.addAttribute("errorMessage", "Please upload a file of size 5MB or less.");
        return "result";
    }
}
