package com.hitices.instance.controller;

import com.hitices.instance.common.MResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangteng
 * @e-mail 1638235292@qq.com
 * @date 2023/5/1
 */
@RestController("/instace")
public class InstanceController {
    @PostMapping("/deploy")
    public MResponse deployInstance() {
        return MResponse.successMResponse();
    }

    @PostMapping("/delete")
    public MResponse deleteInstance() {
        return MResponse.successMResponse();
    }

    @PostMapping("/resourceHistory")
    public MResponse resourceHistory() {
        return MResponse.successMResponse();
    }

    @PostMapping("/status")
    public MResponse status() {
        return MResponse.successMResponse();
    }
}
