package com.hitices.instance.json;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangteng
 * @e-mail 1638235292@qq.com
 * @date 2023/5/31
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class PodResource {
    private List<PodResourceItem> results;
    private Map<String, String> average = new HashMap<>();

    public void addAverage(String key, String value){
        average.put(key, value);
    }
}
