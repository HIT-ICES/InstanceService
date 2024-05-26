package com.hitices.instance.json.deploy;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: wangteng
 * @e-mail: Willtynn@outlook.com
 * @date: 2023/10/12 14:42
 */
@Getter
@Setter
@NoArgsConstructor
public class Metadata {
    private String namespace = "wangteng";
    private Map<String, String> labels = new HashMap<>();
    private String name = "busybox";
//    private Map<String, String> annotations;

    public void addApp(String name){
        labels.put("app",name);
        labels.put("routectl-name",name);
        labels.put("routectl-ns",this.namespace);
    }
}
