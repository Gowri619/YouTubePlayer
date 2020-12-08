package com.example.kotlinsample.model;

import com.example.kotlinsample.di.wrapper.ErrorWrapper;

public class BaseModel {
    private boolean success;
    private ErrorWrapper errors;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ErrorWrapper getErrors() {
        return errors;
    }

    public void setErrors(ErrorWrapper errors) {
        this.errors = errors;
    }
}
