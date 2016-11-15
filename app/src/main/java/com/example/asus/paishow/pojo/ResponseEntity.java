package com.example.asus.paishow.pojo;

import org.xutils.http.annotation.HttpResponse;

/**
 * Created by Administrator on 2016/10/30.
 */

@HttpResponse(parser = ResultParser.class)
public class ResponseEntity {
    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
