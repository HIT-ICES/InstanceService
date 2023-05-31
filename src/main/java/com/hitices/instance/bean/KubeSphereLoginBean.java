package com.hitices.instance.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wangteng
 * @e-mail 1638235292@qq.com
 * @date 2023/5/26
 */
@Getter
@Setter
@AllArgsConstructor
public class KubeSphereLoginBean {
    /**
     * KubeSphere username
     */
    private String username;
    /**
     * KubeSphere password
     */
    private String password;
}
