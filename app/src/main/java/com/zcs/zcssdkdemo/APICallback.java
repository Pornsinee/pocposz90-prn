package com.zcs.zcssdkdemo;

public interface APICallback {
    void onSuccess(String responseData);
    void onFailure();
}