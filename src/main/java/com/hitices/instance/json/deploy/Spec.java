package com.hitices.instance.json.deploy;

import com.hitices.instance.bean.InstanceDeployBean;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

/**
 * @author: wangteng
 * @e-mail: Willtynn@outlook.com
 * @date: 2023/10/12 15:00
 */
@Getter
@Setter
@NoArgsConstructor
public class Spec {
    private int replicas = 1;
    private Selector selector = new Selector();
    private Template template = new Template();
    private Strategy strategy = new Strategy();

    public void addApp(InstanceDeployBean ins){
        selector.addApp(ins);
        template.addApp(ins);
    }

    public void setInfo(InstanceDeployBean ins){
       template.setInfo(ins);
    }
}
