package com.hitices.instance.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author wangteng
 * @e-mail 1638235292@qq.com
 * @date 2023/5/6
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InstanceDeleteBean {
    /**
     * The Cluster name
     */
    private String clusterName;
    /**
     * The namespace
     */
    private String namespace;
    /**
     * The deployment name
     */
    private String deployment;
}
