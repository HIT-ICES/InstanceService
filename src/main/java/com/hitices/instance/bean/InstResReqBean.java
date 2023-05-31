package com.hitices.instance.bean;

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
public class InstResReqBean {
    /**
     * The Cluster name
     */
    private String clusterName;
    /**
     * The namespace
     */
    private String namespace;
    /**
     * The Pod name
     */
    private String podName;
    /**
     * Monitoring start time stamp
     */
    private Long begin;
    /**
     * Monitoring end time stamp
     */
    private Long end;
    /**
     * Monitoring interval in s
     */
    private Long step;
}
