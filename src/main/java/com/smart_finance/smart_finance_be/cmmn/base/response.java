package com.smart_finance.smart_finance_be.cmmn.base;

import java.util.List;

import lombok.Data;

@Data
public class response {
    private List<?> dataList;

    private Object data;

    private String message;
}
