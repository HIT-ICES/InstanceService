package com.hitices.instance.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author wangteng
 * @email willtynn@outlook.com
 * @date 2024/3/13 17:25
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SchemeInstanceBean {
    private String cluster;
    private String name;
    private String namespace;
    private List<InstanceDeployBean> scheme;
}
