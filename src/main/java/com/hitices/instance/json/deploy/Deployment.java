package com.hitices.instance.json.deploy;

import com.hitices.instance.bean.InstanceDeployBean;
import com.hitices.instance.controller.InstanceController;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author: wangteng
 * @e-mail: Willtynn@outlook.com
 * @date: 2023/10/12 15:01
 */
@Getter
@Setter
@NoArgsConstructor
public class Deployment {
    private String apiVersion = "apps/v1";
    private String kind = "Deployment";
    private Metadata metadata = new Metadata();
    private Spec spec = new Spec();

    public void setInfo(InstanceDeployBean ins){
        metadata.setName(ins.getServiceName());
        metadata.setNamespace(ins.getNamespace());
        metadata.addApp(ins.getServiceName());
        spec.setReplicas(ins.getReplicas());
        spec.addApp(ins);
        spec.setInfo(ins);
    }
}
