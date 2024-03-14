package com.hitices.instance.service.impl;

import com.hitices.instance.bean.InstanceDeleteBean;
import com.hitices.instance.bean.InstanceDeployBean;
import com.hitices.instance.bean.SchemeDeployCallbackBean;
import com.hitices.instance.bean.SchemeInstanceBean;
import com.hitices.instance.client.DeploymentServiceClient;
import com.hitices.instance.client.KubeSphereClient;
import com.hitices.instance.common.MResponse;
import com.hitices.instance.json.DeploymentItem;
import com.hitices.instance.json.DeploymentList;
import com.hitices.instance.json.deploy.Deployment;
import com.hitices.instance.service.InstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author wangteng
 * @e-mail 1638235292@qq.com
 * @date 2023/5/1
 */
@Service
public class InstanceServiceImpl implements InstanceService {

    @Autowired
    private KubeSphereClient kubeSphereClient;

    @Autowired
    private DeploymentServiceClient deploymentServiceClient;

    @Override
    @Async
    public void executeDeploymentScheme(SchemeInstanceBean schemeInstanceBean) {
        try {
            DeploymentList deployments = kubeSphereClient.getDeployment(schemeInstanceBean.getCluster(), schemeInstanceBean.getNamespace());
            for (DeploymentItem item : deployments.getItems()){
                kubeSphereClient.deleteDeployment(
                        new InstanceDeleteBean(
                                schemeInstanceBean.getCluster(),
                                schemeInstanceBean.getNamespace(),
                                item.getMetadata().getName()));
            }
            // deploy
            for (InstanceDeployBean instanceDeployBean: schemeInstanceBean.getScheme()){
                Deployment deployment = new Deployment();
                deployment.setInfo(instanceDeployBean);
                kubeSphereClient.createDeployment(deployment);
            }
        }catch (Exception e){
            deploymentServiceClient.deploySchemeCallback(new SchemeDeployCallbackBean(schemeInstanceBean.getId(), 1));
        }
        deploymentServiceClient.deploySchemeCallback(new SchemeDeployCallbackBean(schemeInstanceBean.getId(), 0));
    }
}
