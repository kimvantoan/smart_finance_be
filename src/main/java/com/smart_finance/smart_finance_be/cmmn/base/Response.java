package com.smart_finance.smart_finance_be.cmmn.base;

import java.util.List;

import lombok.Data;

@Data
public class Response {
    private List<?> dataList;

    private Object data;

    private String message;

    public Response setDataList(List<?> dataList) {
        this.dataList = dataList;
        return this;
    }

    public Response setMessage(String message) {
        this.message = message;
        return this;
    }

    public Response setData(Object data) {
        this.data = data;
        return this;
    }
}
