package com.ruoxi.gm.query;

import com.ruoxi.basic.query.BaseQuery;
import com.ruoxi.gm.domain.Employee;
import lombok.Data;

/**
 * @author 若兮
 * @create 2022-02-28 18:37
 */
@Data
public class ShopQuery extends BaseQuery {
    private String name;
    private Integer state;
    private Employee admin;
}
