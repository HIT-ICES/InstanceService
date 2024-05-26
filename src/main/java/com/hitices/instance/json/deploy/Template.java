package com.hitices.instance.json.deploy;

import com.hitices.instance.bean.InstanceDeployBean;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author: wangteng
 * @e-mail: Willtynn@outlook.com
 * @date: 2023/10/12 15:04
 */
@Getter
@Setter
@NoArgsConstructor
public class Template {
    private ContainerMetadata metadata = new ContainerMetadata();
    private TemplateSpec spec = new TemplateSpec();

    public void addApp(InstanceDeployBean ins){
        metadata.addApp(ins);
    }

    public void setInfo(InstanceDeployBean ins){
        Container container = new Container();
        container.setName(ins.getServiceName());
        container.setImage(ins.getImageUrl());
        container.setPorts(ins.getPorts());
        container.setResources(ins.getResources());
        spec.addContainer(container);
        spec.addNode(ins.getNodeName());
    }
}
