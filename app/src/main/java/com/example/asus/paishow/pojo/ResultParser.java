package com.example.asus.paishow.pojo;

import org.xutils.http.app.ResponseParser;
import org.xutils.http.request.UriRequest;

import java.lang.reflect.Type;

/**
 * Created by Administrator on 2016/10/30.
 */

public class ResultParser implements ResponseParser {

    @Override
    public void checkResponse(UriRequest request) throws Throwable {

    }

    @Override
    public Object parse(Type resultType, Class<?> resultClass, String result) throws Throwable {
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setResult(result);
        return responseEntity;
    }
}
