package com.hitices.instance.json.deploy;

import com.hitices.instance.bean.InstanceDeployBean;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: wangteng
 * @e-mail: Willtynn@outlook.com
 * @date: 2023/10/12 14:58
 */
@Getter
@Setter
@NoArgsConstructor
public class Selector {
    private Map<String, String> matchLabels = new HashMap<>();

    public void addApp(InstanceDeployBean ins){
        matchLabels.put("app", ins.getServiceName());
        matchLabels.put("routectl-name", ins.getServiceName());
        matchLabels.put("routectl-ns", ins.getNamespace());
    }
}
