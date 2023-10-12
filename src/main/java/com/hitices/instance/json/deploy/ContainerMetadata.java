package com.hitices.instance.json.deploy;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: wangteng
 * @e-mail: Willtynn@outlook.com
 * @date: 2023/10/12 15:09
 */
@Getter
@Setter
@NoArgsConstructor
public class ContainerMetadata {
    private Map<String, String> labels = new HashMap<>();

    public void addApp(String name){
        labels.put("app",name);
    }
}
