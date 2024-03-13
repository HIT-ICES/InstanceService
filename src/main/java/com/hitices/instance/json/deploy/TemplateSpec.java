package com.hitices.instance.json.deploy;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: wangteng
 * @e-mail: Willtynn@outlook.com
 * @date: 2023/10/12 15:04
 */
@Getter
@Setter
@NoArgsConstructor
public class TemplateSpec {
    private List<Container> containers = new ArrayList<>();
    private String serviceAccount = "default";
    private List<String> initContainers = new ArrayList<>();
    private List<String> volumes = new ArrayList<>();
    private String imagePullSecrets;
    private Map<String, String> nodeSelector = new HashMap<>();

    public void addContainer(Container container){
        containers.add(container);
    }

    public void addNode(String nodeName){
        if (!StringUtils.isEmpty(nodeName)){
            nodeSelector.put("kubernetes.io/hostname", nodeName);
        }
    }
}
