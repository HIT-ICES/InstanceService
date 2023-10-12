package com.hitices.instance.json.deploy;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author: wangteng
 * @e-mail: Willtynn@outlook.com
 * @date: 2023/10/12 15:01
 */
@Getter
@Setter
@NoArgsConstructor
public class RollingUpdate {
    private String maxUnavailable = "25%";
    private String maxSurge = "25%";
}
