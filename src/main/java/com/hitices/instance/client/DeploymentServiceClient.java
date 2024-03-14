package com.hitices.instance.client;

import com.hitices.instance.bean.SchemeDeployCallbackBean;
import com.hitices.instance.common.MResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "InstanceService", url = "http://deployment-service:8080")
public interface DeploymentServiceClient {
    @RequestMapping(value = "/deployment/scheme/deploy/callback", method = RequestMethod.POST)
    void deploySchemeCallback(@RequestBody SchemeDeployCallbackBean mResponse);
}
