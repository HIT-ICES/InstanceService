package com.hitices.instance.service;

import com.hitices.instance.bean.InstanceDeleteBean;
import com.hitices.instance.bean.SchemeInstanceBean;
import org.springframework.stereotype.Service;

/**
 * @author wangteng
 * @e-mail 1638235292@qq.com
 * @date 2023/5/1
 */

public interface InstanceService {
    public void executeDeploymentScheme(SchemeInstanceBean schemeInstanceBean);
}
