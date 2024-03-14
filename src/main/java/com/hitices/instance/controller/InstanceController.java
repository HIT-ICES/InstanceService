package com.hitices.instance.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.hitices.instance.bean.*;
import com.hitices.instance.client.KubeSphereClient;
import com.hitices.instance.common.MResponse;
import com.hitices.instance.json.*;
import com.hitices.instance.json.deploy.Deployment;
import com.hitices.instance.json.deploy.Metadata;
import com.hitices.instance.service.InstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

/**
 * @author wangteng
 * @e-mail 1638235292@qq.com
 * @date 2023/5/1
 */
@CrossOrigin
@RestController
@RequestMapping("/instance")
public class InstanceController {

    @Autowired
    private KubeSphereClient kubeSphereClient;

    @Autowired
    private InstanceService instanceService;

    @PostMapping("/deploy")
    public MResponse deployInstance(@RequestBody InstanceDeployBean instanceDeployBean) {
        Deployment deployment = new Deployment();
        deployment.setInfo(instanceDeployBean);
        try {
            kubeSphereClient.createDeployment(deployment);
        }catch (Exception e){
           return MResponse.failedMResponse().setStatus(e.getMessage());
        }
        return MResponse.successMResponse();
    }

    @PostMapping("/service")
    public int deployService(@RequestBody ServiceCreateBean serviceCreateBean) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Resource resource = new ClassPathResource("services.json");
            byte[] jsonData = FileCopyUtils.copyToByteArray(resource.getInputStream());
            String jsonString = new String(jsonData, StandardCharsets.UTF_8);
            JsonObject templateData = gson.fromJson(jsonString, JsonObject.class);
            // metadata
            JsonObject metadata = templateData.getAsJsonObject("metadata");
            metadata.addProperty("name", serviceCreateBean.getName());
            metadata.addProperty("namespace", serviceCreateBean.getNamespace());
            metadata.getAsJsonObject("labels").addProperty("app", serviceCreateBean.getName());
            // Spec
            JsonObject Spec = templateData.getAsJsonObject("spec");
            Spec.getAsJsonObject("selector").addProperty("app", serviceCreateBean.getName());
            JsonObject Port = Spec.getAsJsonArray("ports").get(0).getAsJsonObject();
            Port.addProperty("name", serviceCreateBean.getPort().getName());
            Port.addProperty("protocol", serviceCreateBean.getPort().getProtocol());
            Port.addProperty("targetPort", serviceCreateBean.getPort().getContainerPort());
            Port.addProperty("port", serviceCreateBean.getPort().getContainerPort());
            int nodePort = kubeSphereClient.createService(templateData, "ices104", serviceCreateBean.getNamespace());
            return nodePort;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/delete")
    public MResponse deleteInstance(@RequestBody InstanceDeleteBean instanceDeleteBean) {
        kubeSphereClient.deleteDeployment(instanceDeleteBean);
        return MResponse.successMResponse();
    }

    @PostMapping("/scheme/deploy")
    public MResponse deployInstanceScheme(@RequestBody SchemeInstanceBean schemeInstanceBean){
        instanceService.executeDeploymentScheme(schemeInstanceBean);
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

    @GetMapping("/status/service")
    public MResponse getPodByService(@RequestParam("cluster") String cluster, @RequestParam("service") String service) {
        PodList podList = kubeSphereClient.getPodByService(cluster, service);
        if (podList!=null){
            return MResponse.successMResponse().data(podList);
        }
        return MResponse.failedMResponse();
    }

    @GetMapping("/namespaces")
    public MResponse namespaces(@RequestParam("cluster") String cluster,@RequestParam("limit") int limit,@RequestParam("page") int page) {
        Map<String, Object> result = kubeSphereClient.getNamespace(cluster,limit, page);
        if (result!=null){
            return MResponse.successMResponse().data(result);
        }
        return MResponse.failedMResponse();
    }

    @GetMapping("/node")
    public MResponse status(@RequestParam("cluster") String cluster, @RequestParam("nodeName") String nodeName, @RequestParam("limit") int limit , @RequestParam("page") int page) {
        PodList podList = kubeSphereClient.getNodePod(cluster, nodeName,limit,page);
        if (podList!=null){
            return MResponse.successMResponse().data(podList);
        }
        return MResponse.failedMResponse();
    }
}
