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
public class Strategy {
    private String type = "RollingUpdate";
    private RollingUpdate rollingUpdate = new RollingUpdate();
}
