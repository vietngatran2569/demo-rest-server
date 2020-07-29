package com.example.restdemo2.domain.rest;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class RESTResponse {
    private HashMap<String, Object> response;
    private int status;
    private String message;
    private List<Object> datas;
    private RESTPagination pagination;

    // MUST be private.
    public RESTResponse() {
        this.response = new HashMap<>();
        this.datas = new ArrayList<>();
    }

    public Map<String, Object> getResponse() {
        return response;
    }

    private void addResponse(String key, Object value) {
        this.response.put(key, value);
    }

    public RESTResponse setStatus(int code) {
        this.status = code;
        return this;
    }

    public RESTResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public RESTResponse setData(Object obj) {
        this.datas.add(obj);
        return this;
    }

    public RESTResponse setDatas(List listObj) {
        this.datas.addAll(listObj);
        return this;
    }

    public RESTResponse setPagination(RESTPagination pagination) {
        this.pagination = pagination;
        return this;
    }

    public static RESTResponse Builder() {
        return new RESTResponse();
    }

    public Map<String, Object> build() {
        this.addResponse("status", this.status);
        this.addResponse("message", this.message);
        this.addResponse("data", this.datas);
        if (this.pagination != null) {
            this.addResponse("pagination", this.pagination);
        }
        return this.getResponse();
    }
}
