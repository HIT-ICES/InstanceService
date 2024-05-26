package com.hitices.instance.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.hitices.instance.bean.*;
import com.hitices.instance.client.KubeSphereClient;
import com.hitices.instance.common.MResponse;
import com.hitices.instance.json.*;
import com.hitices.instance.json.deploy.Deployment;
import com.hitices.instance.service.InstanceService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.*;

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
     * Get the resource history of pod in a given namespace under a cluster between timestamp
     * @param instResReqBean information of pod
     * @return podResource contain cpu, mem, net
     */
    @PostMapping("/resourceHistory/export")
    public ResponseEntity<byte[]> exportResourceHistory(@RequestBody InstResReqBean instResReqBean) {
        try {
            PodResource podResource = kubeSphereClient.getPodResource(instResReqBean);
            // 创建Excel工作簿
            Map<String, List<Double>> data = new HashMap<>();
            Map<String, Double> sums = new HashMap<>();
            Workbook workbook = new XSSFWorkbook();
            // 原始数据
            for (PodResourceItem item: podResource.getResults()){
                Sheet sheet = workbook.createSheet(item.getMetric_name());
                data.put(item.getMetric_name(),new ArrayList<>());
                Row row = sheet.createRow(0);
                Cell cell = row.createCell(0);
                cell.setCellValue("time");
                cell = row.createCell(1);
                cell.setCellValue("value");
                int i = 1;
                double sum = 0;
                for (List value:item.getData().getResult().get(0).getValues()){
                    row = sheet.createRow(i);
                    cell = row.createCell(0);
                    cell.setCellValue(value.get(0).toString());
                    cell = row.createCell(1);
                    cell.setCellValue(value.get(1).toString());
                    data.get(item.getMetric_name()).add(Double.valueOf(value.get(1).toString()));
                    sum += Double.valueOf(value.get(1).toString());
                    i += 1;
                }
                sums.put(item.getMetric_name(),sum);
            }
            // 统计数据
            Sheet sheet = workbook.createSheet("result");
            Row row = sheet.createRow(0);
            Cell cell = row.createCell(0);
            cell.setCellValue("item");
            cell = row.createCell(1);
            cell.setCellValue("P99");
            cell = row.createCell(2);
            cell.setCellValue("P95");
            cell = row.createCell(3);
            cell.setCellValue("P50");
            cell = row.createCell(4);
            cell.setCellValue("AVG");
            int i = 1;
            for (String key:data.keySet()){
                List<Double> values = data.get(key);
                Collections.sort(values);
                row = sheet.createRow(i);
                cell = row.createCell(0);
                cell.setCellValue(key);
                cell = row.createCell(1);
                cell.setCellValue(values.get((int)Math.ceil(values.size() * 0.99)-1));
                cell = row.createCell(2);
                cell.setCellValue(values.get((int)Math.ceil(values.size() * 0.95)-1));
                cell = row.createCell(3);
                cell.setCellValue(values.get((int)Math.ceil(values.size() * 0.50)-1));
                cell = row.createCell(4);
                cell.setCellValue(sums.get(key)/values.size());
                i += 1;
            }
            // 构建HTTP响应，将C文件返回给用户
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);

            byte[] csvBytes = outputStream.toByteArray();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("text/csv"));
            headers.setContentDispositionFormData("filename", "resource.xlsx");
            return new ResponseEntity<>(csvBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
