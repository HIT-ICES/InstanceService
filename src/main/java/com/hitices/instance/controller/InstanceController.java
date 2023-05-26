package com.hitices.instance.controller;

import com.hitices.instance.bean.InstResReqBean;
import com.hitices.instance.bean.InstanceDeleteBean;
import com.hitices.instance.bean.InstanceDeployBean;
import com.hitices.instance.client.KubeSphereClient;
import com.hitices.instance.common.MResponse;
import com.hitices.instance.json.PodList;
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
        return MResponse.successMResponse();
    }

    @PostMapping("/resourceHistory")
    public MResponse resourceHistory(@RequestBody InstResReqBean instResReqBean) {
        return MResponse.successMResponse();
    }

    @GetMapping("/status")
    public MResponse status(@RequestParam("cluster") String cluster, @RequestParam("namespace") String namespace) {
        PodList podList = kubeSphereClient.getPodStatus(cluster, namespace);
        if (podList!=null){
            return MResponse.successMResponse().data(podList);
        }
        return MResponse.failedMResponse();
    }
}
