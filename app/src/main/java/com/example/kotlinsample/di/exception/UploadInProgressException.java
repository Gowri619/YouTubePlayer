package com.example.kotlinsample.di.exception;


public class UploadInProgressException extends Exception {
    public UploadInProgressException() {
        super("Upload is in progress");
    }

    public UploadInProgressException(String message) {
        super(message);
    }
}
