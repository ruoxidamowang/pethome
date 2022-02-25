package com.ruoxi.basic.query;

import lombok.Data;

/**
 * @author 若兮
 * @create 2022-02-21 14:03
 */
@Data
public class BaseQuery {
    private Integer start = 1;
    private Integer pageSize = 5;
}
