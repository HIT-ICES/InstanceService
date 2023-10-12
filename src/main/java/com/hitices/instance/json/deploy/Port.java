package com.hitices.instance.json.deploy;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author: wangteng
 * @e-mail: Willtynn@outlook.com
 * @date: 2023/10/12 14:59
 */
@Getter
@Setter
@NoArgsConstructor
public class Port {
    private String name;
    private String protocol;
    private int containerPort;
}

