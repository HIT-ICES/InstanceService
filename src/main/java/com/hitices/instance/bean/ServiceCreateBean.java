package com.hitices.instance.bean;

import com.hitices.instance.json.deploy.Port;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author wangteng
 * @email willtynn@outlook.com
 * @date 2024/3/14 19:02
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceCreateBean {
    private String name;
    private String instanceName;
    private String namespace;
    private Port port;
}
