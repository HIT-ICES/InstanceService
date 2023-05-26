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
     * KubeSphere get pod status api path
     */
    public static String status = "/kapis/clusters/%s/resources.kubesphere.io/v1alpha3/namespaces/%s/pods";

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
