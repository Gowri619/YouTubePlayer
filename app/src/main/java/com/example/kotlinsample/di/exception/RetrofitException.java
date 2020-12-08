package com.example.kotlinsample.di.exception;

import android.text.TextUtils;

import com.example.kotlinsample.Constants;
import com.example.kotlinsample.model.BaseModel;
import com.example.kotlinsample.di.wrapper.ErrorWrapper;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RetrofitException extends Exception {

    private Response response;
    private final int code;
    private String message;
    private final Retrofit retrofit;
    private final RetrofitErrorTypeEnum type;
    private List<String> backupErrorList;

    public RetrofitException(Response response, int code, String message, Retrofit retrofit, RetrofitErrorTypeEnum type) {
        this.response = response;
        this.code = code;
        this.retrofit = retrofit;
        this.type = type;
        backupErrorList = new ArrayList<>();
        message = TextUtils.isEmpty(message) ? Constants.EMPTY_STRING : message;
        backupErrorList.add(message);

        extractHttpError();
    }

    private void extractHttpError() {
        BaseModel errorModel = null;

        /**
         * This is a chance to create ErrorModel for specific API and convert error body to it.
         * When {@link com.idpalorg.data.util.Util.getErrorMessage()} is called it will use message
         * from ErrorModel for displaying purposes
         */

        if (type == RetrofitErrorTypeEnum.HTTP && retrofit != null && response != null) {
            try {
                Converter<ResponseBody, BaseModel> converter = retrofit.responseBodyConverter(BaseModel.class, new Annotation[0]);
                errorModel = converter.convert(response.errorBody());
            } catch (IOException e) {
            }
        }

        if (errorModel == null) {
            errorModel = new BaseModel();
            errorModel.setSuccess(false);
            ErrorWrapper errorWrapper = new ErrorWrapper();
            HashMap<String, List<String>> errors = new HashMap<>();
            errors.put("unknown", backupErrorList);
            errorWrapper.setMapErrors(errors);
            errorModel.setErrors(errorWrapper);
        }

        this.message = errorModel.getErrors().getMessage();
    }

    public String getRetrofitErrorMessage() {
        return message;
    }

    public RetrofitErrorTypeEnum getType() {
        return type;
    }

    public static RetrofitException httpException(Response response, Retrofit retrofit) {
        if (response.code() == 503){
            Constants.Response_Code = response.code();
            Constants.Response_Message = response.headers().get("message").replaceAll("[\\[\\]]", "");
            Constants.Response_uptime = response.headers().get("server_up_time").replaceAll("[\\[\\]]", "");
        }
        return new RetrofitException(response, response.code(), response.message(), retrofit, RetrofitErrorTypeEnum.HTTP);
    }

    public static RetrofitException networkException(IOException ioException) {
        String message = TextUtils.isEmpty(ioException.getMessage()) ? ioException.getClass().getName() : ioException.getMessage();
        return new RetrofitException(null, Constants.NETWORK_EXCEPTION_CODE, ioException.getMessage(), null, RetrofitErrorTypeEnum.NETWORK);
    }

    public static RetrofitException unknownException(Throwable throwable) {
        String message = TextUtils.isEmpty(throwable.getMessage()) ? throwable.getClass().getName() : throwable.getMessage();
        return new RetrofitException(null, Constants.UNKNOWN_EXCEPTION_CODE, message, null, RetrofitErrorTypeEnum.UNKNOWN);
    }

    public enum RetrofitErrorTypeEnum {
        HTTP,
        NETWORK,
        UNKNOWN
    }
}
