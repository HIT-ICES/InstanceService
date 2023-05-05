package com.hitices.instance.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wangteng
 * @e-mail 1638235292@qq.com
 * @date 2023/5/1
 */
@Getter
public class MResponse {
    private String status;
    private int code;

    public static final int successCode = 0;
    public static final int failedCode = 1;

    private Map<String, Object> valueMap = new HashMap<>();

    public Object get(String key) {
        return this.valueMap.getOrDefault(key, null);
    }

    public MResponse set(String key, Object value) {
        this.valueMap.put(key, value);
        return this;
    }

    public MResponse setCode(int code) {
        this.code = code;
        return this;
    }

    public MResponse setStatus(String status) {
        this.status = status;
        return this;
    }

    public static MResponse successMResponse() {
        return new MResponse().setCode(successCode).setStatus("success");
    }

    public static MResponse failedMResponse() {
        return new MResponse().setCode(failedCode).setStatus("failed");
    }

    public <T extends Map> void setData(T data) {
        ObjectMapper mapper = new ObjectMapper();
        this.valueMap = mapper.convertValue(data, Map.class);
    }

    public <T> T getData(Class<T> clz) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(this.valueMap, clz);
    }
}
