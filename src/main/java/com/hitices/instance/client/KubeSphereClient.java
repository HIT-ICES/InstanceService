package com.hitices.instance.client;

import com.google.gson.JsonPrimitive;
import com.hitices.instance.bean.InstResReqBean;
import com.hitices.instance.bean.InstanceDeleteBean;
import com.hitices.instance.bean.KubeSphereLoginBean;
import com.hitices.instance.common.MResponse;
import com.hitices.instance.config.KubeSphereConfig;
import com.hitices.instance.json.*;
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

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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

    public Map<String, Object> getNamespace(String cluster, int limit, int page){
        Map<String, Object> result = new HashMap<>();
        List<String> list = new ArrayList<>();
        String json =  restTemplate.getForEntity(KubeSphereConfig.url+String.format(KubeSphereConfig.namespace,cluster, page, limit), String.class).getBody();
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(json).getAsJsonObject();
        JsonArray items = jsonObject.getAsJsonArray("items");
        JsonPrimitive total = jsonObject.getAsJsonPrimitive("totalItems");
        for (int i = 0 ; i < items.size(); i++){
            JsonObject firstItem = items.get(i).getAsJsonObject();
            JsonObject metadata = firstItem.getAsJsonObject("metadata");
            String name = metadata.get("name").getAsString();
            list.add(name);
        }
        result.put("items", list);
        result.put("total",total.getAsBigInteger());
        return result;
    }

    public PodList getPodStatus(String cluster, String namespace){
        return restTemplate.getForEntity(KubeSphereConfig.url+String.format(KubeSphereConfig.status,cluster,namespace), PodList.class).getBody();
    }

    public PodList getPodByService(String cluster, String service){
        PodList podList =  restTemplate.getForEntity(KubeSphereConfig.url+String.format(KubeSphereConfig.pods,cluster), PodList.class).getBody();
        podList.setItems(podList.getItems().stream()
                .filter(podItem -> podItem.getMetadata().getLabels().get("app")!=null && podItem.getMetadata().getLabels().get("app").equals(service))
                .collect(Collectors.toList()));
        podList.setTotalItems(podList.getItems().size());
        return podList;
    }

    public PodResource getPodResource(InstResReqBean inst){
        DecimalFormat decimalFormat = new DecimalFormat("#.############");
        PodResource resource = restTemplate.getForEntity(KubeSphereConfig.url+String.format(KubeSphereConfig.resource,
                inst.getClusterName(),
                inst.getNamespace(),
                inst.getPodName(),
                inst.getBegin(),
                inst.getEnd(),
                inst.getStep()), PodResource.class).getBody();
        for (PodResourceItem podResourceItem : resource.getResults()){
            String name = podResourceItem.getMetric_name();
            Double sum = 0.0;
            Integer count = podResourceItem.getData().getResult().get(0).getValues().size();
            for (List<String> value : podResourceItem.getData().getResult().get(0).getValues()){
                sum += Double.valueOf(value.get(1));
            }
            resource.addAverage(name, decimalFormat.format(sum/count));
        }
        return resource;
    }

    public DeploymentList getDeployment(String cluster, String namespace){
        return restTemplate.getForEntity(KubeSphereConfig.url+String.format(KubeSphereConfig.get_deploy,cluster,namespace), DeploymentList.class).getBody();
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

    public PodList getPodByName(String podName){
        return restTemplate.getForEntity(KubeSphereConfig.url+String.format(KubeSphereConfig.pod_name ,podName), PodList.class).getBody();
    }
}
