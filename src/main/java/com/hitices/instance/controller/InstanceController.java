package com.hitices.instance.controller;

import com.hitices.instance.bean.InstResReqBean;
import com.hitices.instance.bean.InstanceDeleteBean;
import com.hitices.instance.bean.InstanceDeployBean;
import com.hitices.instance.client.KubeSphereClient;
import com.hitices.instance.common.MResponse;
import com.hitices.instance.json.PodList;
import com.hitices.instance.json.PodResource;
import com.hitices.instance.json.PodResourceData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wangteng
 * @e-mail 1638235292@qq.com
 * @date 2023/5/1
 */
@RestController
@RequestMapping("/instance")
public class InstanceController {

    @Autowired
    private KubeSphereClient kubeSphereClient;

    @PostMapping("/deploy")
    public MResponse deployInstance(@RequestBody InstanceDeployBean instanceDeployBean) {
        System.out.println(kubeSphereClient.login());
        return MResponse.successMResponse();
    }

    @PostMapping("/delete")
    public MResponse deleteInstance(@RequestBody InstanceDeleteBean instanceDeleteBean) {
        kubeSphereClient.deleteDeployment(instanceDeleteBean);
        return MResponse.successMResponse();
    }

    /**
     * Get the resource history of pod in a given namespace under a cluster between timestamp
     * @param instResReqBean information of pod
     * @return podResource contain cpu, mem, net
     */
    @PostMapping("/resourceHistory")
    public MResponse resourceHistory(@RequestBody InstResReqBean instResReqBean) {
        PodResource podResource = kubeSphereClient.getPodResource(instResReqBean);;
        if (podResource!=null){
            return MResponse.successMResponse().data(podResource);
        }
        return MResponse.failedMResponse();
    }

    /**
     * Get the status of containers in a given namespace under a cluster
     * @param cluster cluster name
     * @param namespace namespace
     * @return podList
     */
    @GetMapping("/status")
    public MResponse status(@RequestParam("cluster") String cluster, @RequestParam("namespace") String namespace) {
        PodList podList = kubeSphereClient.getPodStatus(cluster, namespace);
        if (podList!=null){
            return MResponse.successMResponse().data(podList);
        }
        return MResponse.failedMResponse();
    }
}
