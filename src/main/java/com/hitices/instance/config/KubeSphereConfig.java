package com.hitices.instance.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author wangteng
 * @e-mail 1638235292@qq.com
 * @date 2023/5/26
 */
@Configuration
public class KubeSphereConfig{
    /**
     * The KubeSphere url
     */
    public static String url;

    /**
     * KubeSphere username
     */
    public static String username;

    /**
     * KubeSphere password
     */
    public static String password;

    /**
     * KubeSphere login api path
     */
    public static String login = "/kapis/iam.kubesphere.io/v1alpha2/login";

    /**
     * KubeSphere create deployment
     */
    public static String deploy = "/apis/clusters/%s/apps/v1/namespaces/%s/deployments";

    /**
     * KubeSphere get pod status api path
     */
    public static String status = "/kapis/clusters/%s/resources.kubesphere.io/v1alpha3/namespaces/%s/pods";

    public static String pods = "/kapis/clusters/%s/resources.kubesphere.io/v1alpha3/pods";

    /**
     * KubeSphere get pod resource api path
     */
    public static String resource = "/kapis/clusters/%s/monitoring.kubesphere.io/v1alpha3/namespaces/%s/pods/%s" +
            "?start=%d&end=%d&step=%ds&times=50&metrics_filter=pod_cpu_usage|pod_memory_usage_wo_cache|pod_net_bytes_transmitted|pod_net_bytes_received$";

    /**
     * KubeSphere delete deployment api path
     */
    public static String delete = "/apis/clusters/%s/apps/v1/namespaces/%s/deployments/%s";

    public static String node_pod = "/kapis/clusters/%s/resources.kubesphere.io/v1alpha3/pods?limit=%d&nodeName=%s&page=%d&sortBy=startTime";

    public static String namespace = "/kapis/clusters/%s/resources.kubesphere.io/v1alpha3/namespaces?page=%d&limit=%d&sortBy=createTime";

    @Value("${KubeSphereConfig.url}")
    public void setUrl(String url) {
        KubeSphereConfig.url = url;
    }

    @Value("${KubeSphereConfig.username}")
    public void setUsername(String username) {
        KubeSphereConfig.username = username;
    }

    @Value("${KubeSphereConfig.password}")
    public void setPassword(String password) {
        KubeSphereConfig.password = password;
    }
}
