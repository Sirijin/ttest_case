package com.example.demo.controller;

import org.springframework.validation.BindingResult;

import static com.example.demo.util.ErrorsUtil.returnErrorsToClient;

public class ErrorHandler {
    public static void bindingHandle(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            returnErrorsToClient(bindingResult);
        }
    }
}
