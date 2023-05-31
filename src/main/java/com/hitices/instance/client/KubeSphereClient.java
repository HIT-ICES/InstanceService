package com.hitices.instance.client;

import com.hitices.instance.bean.InstResReqBean;
import com.hitices.instance.bean.InstanceDeleteBean;
import com.hitices.instance.bean.KubeSphereLoginBean;
import com.hitices.instance.config.KubeSphereConfig;
import com.hitices.instance.json.PodList;
import com.hitices.instance.json.PodResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;

/**
 * @author wangteng
 * @e-mail 1638235292@qq.com
 * @date 2023/5/6
 */
@Slf4j
@Component
public class KubeSphereClient {

    public static RestTemplate restTemplate = new RestTemplate();

    public String login(){
        return (String) restTemplate.postForEntity(KubeSphereConfig.url+KubeSphereConfig.login,
                new KubeSphereLoginBean(KubeSphereConfig.username,KubeSphereConfig.password), LinkedHashMap.class).getBody().get("access_token");
    }

    public PodList getPodStatus(String cluster, String namespace){
        return restTemplate.getForEntity(KubeSphereConfig.url+String.format(KubeSphereConfig.status,cluster,namespace), PodList.class).getBody();
    }

    public PodResource getPodResource(InstResReqBean inst){
        return restTemplate.getForEntity(KubeSphereConfig.url+String.format(KubeSphereConfig.resource,
                inst.getClusterName(),
                inst.getNamespace(),
                inst.getPodName(),
                inst.getBegin(),
                inst.getEnd(),
                inst.getStep()), PodResource.class).getBody();
    }

    public void deleteDeployment(InstanceDeleteBean ins){
        System.out.println(KubeSphereConfig.url+String.format(KubeSphereConfig.delete,
                ins.getClusterName(),
                ins.getNamespace(),
                ins.getDeployment()));
        restTemplate.delete(KubeSphereConfig.url+String.format(KubeSphereConfig.delete,
                ins.getClusterName(),
                ins.getNamespace(),
                ins.getDeployment()));
    }

}
