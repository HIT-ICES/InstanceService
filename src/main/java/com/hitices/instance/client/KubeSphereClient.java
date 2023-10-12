package com.hitices.instance.client;

import com.hitices.instance.bean.InstResReqBean;
import com.hitices.instance.bean.InstanceDeleteBean;
import com.hitices.instance.bean.KubeSphereLoginBean;
import com.hitices.instance.config.KubeSphereConfig;
import com.hitices.instance.json.PodList;
import com.hitices.instance.json.PodMetadata;
import com.hitices.instance.json.PodResource;
import com.hitices.instance.json.deploy.Deployment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

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
        restTemplate.delete(KubeSphereConfig.url+String.format(KubeSphereConfig.delete,
                ins.getClusterName(),
                ins.getNamespace(),
                ins.getDeployment()));
    }

    public void createDeployment(Deployment deployment){
         HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Deployment> request = new HttpEntity<>(deployment, headers);

        restTemplate.postForEntity(KubeSphereConfig.url+String.format(KubeSphereConfig.deploy,"ices104","wangteng"),request, String.class);
    }

    public PodList getNodePod(String cluster,String nodeName, int limit, int page){
        return restTemplate.getForEntity(KubeSphereConfig.url+String.format(KubeSphereConfig.node_pod,cluster, limit, nodeName, page), PodList.class).getBody();
    }
}
