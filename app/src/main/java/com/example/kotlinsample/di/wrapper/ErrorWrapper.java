package com.example.kotlinsample.di.wrapper;

import com.example.kotlinsample.Constants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class ErrorWrapper {
    private HashMap<String, List<String>> mapErrors;
    private String message;

    public HashMap<String, List<String>> getMapErrors() {
        return mapErrors;
    }

    public void setMapErrors(HashMap<String, List<String>> mapErrors) {
        this.mapErrors = mapErrors;
        this.message = getFirstError();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    /* Date: 22-07-2019 , Name: AIT - ANDROID  , Release Version: 2.5.0
        Desc : Removed for loop and added if condition for executing one piece of code*/
    public String getFirstError() {
        Collection<List<String>> errorLists = getSafeErrors().values();

        if (errorLists.iterator().hasNext()) {
            return errorLists.iterator().next().get(0);
        }

        return "Unknown error occurred.";
    }

    private HashMap<String, List<String>> getSafeErrors() {
        if (mapErrors == null) {
            HashMap<String, List<String>> defaultError = new HashMap<>();
            List<String> errors = new ArrayList<>();
            errors.add("Unknown error occurred.");
            defaultError.put(Constants.ERROR, errors);
            return defaultError;
        } else {
            return mapErrors;
        }
    }
}
