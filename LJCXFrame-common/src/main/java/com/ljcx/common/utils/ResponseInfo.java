package com.ljcx.common.utils;

import com.ljcx.common.enums.LJHttpStatus;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Data
public class ResponseInfo<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private String msg = LJHttpStatus.OK.getValue();

    private int code = LJHttpStatus.OK.getCode();

    private T data;

    public ResponseInfo() {
        super();
    }


    public ResponseInfo success(String msg){
        return new ResponseInfo(LJHttpStatus.OK.getCode(),msg);
    }

    public ResponseInfo failed(String msg){
        return new ResponseInfo(LJHttpStatus.FAILED.getCode(),msg);
    }

    public ResponseInfo(T data) {
        super();
        this.data = data;
    }

    public ResponseInfo(int code,String msg) {
        super();
        this.msg = msg;
        this.code = code;
    }
}

