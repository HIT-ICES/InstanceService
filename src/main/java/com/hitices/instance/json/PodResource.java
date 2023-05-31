package com.hitices.instance.json;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author wangteng
 * @e-mail 1638235292@qq.com
 * @date 2023/5/31
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class PodResource {
    private List<PodResourceItem> results;
}
