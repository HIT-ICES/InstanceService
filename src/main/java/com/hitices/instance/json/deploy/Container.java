package com.hitices.instance.json.deploy;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: wangteng
 * @e-mail: Willtynn@outlook.com
 * @date: 2023/10/12 14:59
 */
@Getter
@Setter
@NoArgsConstructor
public class Container {
    private String name;
    private String imagePullPolicy = "IfNotPresent";
    private List<Port> ports = new ArrayList<>();
    private String image;
    private Resources resources = new Resources();
}
