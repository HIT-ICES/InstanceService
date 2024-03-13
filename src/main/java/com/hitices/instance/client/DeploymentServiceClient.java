package com.hitices.instance.client;

import com.hitices.instance.common.MResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "InstanceService", url = "http://127.0.0.1:8080")
public interface DeploymentServiceClient {
    @RequestMapping(value = "/deployment/scheme/deploy/callback", method = RequestMethod.POST)
    void deploySchemeCallback(@RequestBody MResponse mResponse);
}
