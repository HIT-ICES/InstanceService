package com.hitices.instance.json;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author wangteng
 * @email willtynn@outlook.com
 * @date 2024/3/13 15:18
 */
@Getter
@Setter
@NoArgsConstructor
public class DeploymentList {
    private List<DeploymentItem> items;
    private Integer totalItems;
}
